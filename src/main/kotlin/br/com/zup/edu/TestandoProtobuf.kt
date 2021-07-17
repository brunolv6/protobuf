package br.com.zup.edu

import java.io.FileInputStream
import java.io.FileOutputStream

fun main() {
    val request =  FuncionarioRequest.newBuilder()
        .setNome("Bruno Valle")
        .setCpf("000.000.000-00")
        .setSalario(2000.80)
        .setAtivo(true)
        .setCargo(Cargo.DEV) // este valor nem aparecerá no texto, pq ele é zero, portanto default, e é o que o outro lado assumirá que é, caso não haja nada
        .addEnderecos(FuncionarioRequest.Endereco.newBuilder()
            .setLogradouro("Avenida Lo")
            .setCep("00000-000")
            .setComplemento("Casa A")
            .build())
        .addEnderecos(FuncionarioRequest.Endereco.newBuilder()
            .setLogradouro("Avenida Lo 2")
            .setCep("00000-002")
            .setComplemento("Casa B")
            .build())
        .build()

    // informacao em forma de texto
    println(request)

    // SERIALIZAR - ESCREVENDO - transforma informacao em bytes e cria um arquivo com este conteudo
    request.writeTo(FileOutputStream("funcionario-request.bin"))

    // DESERIALIZAR - LENDO - vou ler o arquivo binário e usá-lo para criar um novo FuncionarioRequest
    val request2 = FuncionarioRequest.newBuilder().mergeFrom(FileInputStream("funcionario-request.bin"))

    // modificando objeto
    request2.setCargo(Cargo.GERENTE)

    // imprimindo em forma de texto
    println(request2)

}
