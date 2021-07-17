package br.com.zup.edu

import com.google.protobuf.Timestamp
import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver
import java.time.LocalDateTime
import java.time.ZoneId

fun main() {
    val server = ServerBuilder
        .forPort(50051)
        .addService(FuncionarioEndpoint())
        .build()

    // inicializa o servidor
    server.start()

    // espera que alguem o feche
    server.awaitTermination()
}

// FuncionarioServiceGrpc.FuncionarioServiceImplBase é abstrata por isto deve ser criada outra
class FuncionarioEndpoint : FuncionarioServiceGrpc.FuncionarioServiceImplBase() {

    // método para o cadastro que possui um responseObserver porque ele consegue trabalhar com um modelo cíclico de Stream (acho que sem fechar a trend - não é certeza)
    override fun cadastrar(request: FuncionarioRequest?, responseObserver: StreamObserver<FuncionarioResponse>?) {

        println(request!!)

        var nome: String? = request.nome

        if(!request.hasField(FuncionarioRequest.getDescriptor().findFieldByName("nome"))){
            nome = "[???]"
        }

        val instant = LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant()
        val criadoEm = Timestamp.newBuilder()
            .setSeconds(instant.epochSecond)
            .setNanos(instant.nano)
            .build()

        // construção de uma resposta
        val response = FuncionarioResponse.newBuilder()
            .setNome(nome)
            .setCriadoEM(criadoEm)
            .build()

        // envia resposta a quem fez a requisição
        responseObserver?.onNext(response)

        // finaliza a parte do reponse - e fecha a conexão - eu acho, sem certeza
        responseObserver?.onCompleted()
    }
}