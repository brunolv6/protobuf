package br.com.zup

import br.com.zup.edu.Cargo
import br.com.zup.edu.FuncionarioRequest
import br.com.zup.edu.FuncionarioServiceGrpc
import io.grpc.ManagedChannelBuilder

fun main() {

    // definiremos que tipos de canais usaremos TLS HTTP/2 e coisa do tipo
    val channel = ManagedChannelBuilder
        .forAddress("localhost", 50051)
        .usePlaintext() // define que usarei texto mesmo, porém posso definir como TLS se preferir trocando este método para useTransportSecurity()
        .build()

    // definirmos que servico utilizarei e definirei o canal e o como
    val client = FuncionarioServiceGrpc.newBlockingStub(channel)

    val request =  FuncionarioRequest.newBuilder()
        .setNome("Bruno Valle")
        .setCpf("000.000.000-00")
        .setIdade(21)
        .setSalario(2000.80)
        .setAtivo(true)
        .setCargo(Cargo.DEV) // este valor nem aparecerá no texto, pq ele é zero, portanto default, e é o que o outro lado assumirá que é, caso não haja nada
        .addEnderecos(
            FuncionarioRequest.Endereco.newBuilder()
            .setLogradouro("Avenida Lo")
            .setCep("00000-000")
            .setComplemento("Casa A")
            .build())
        .addEnderecos(
            FuncionarioRequest.Endereco.newBuilder()
            .setLogradouro("Avenida Lo 2")
            .setCep("00000-002")
            .setComplemento("Casa B")
            .build())
        .build()

    val response = client.cadastrar(request)

    print(response)
}