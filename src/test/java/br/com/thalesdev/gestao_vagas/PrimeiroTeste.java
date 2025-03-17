package br.com.thalesdev.gestao_vagas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PrimeiroTeste {

    @Test
    public void deve_ser_possivel_calcular_dois_numeros() {
        var resultado = calculate(10, 20);
        assertEquals(30, resultado);
    }

    public static int calculate(double a, double b) {
        return (int) (a + b);
    }

}
