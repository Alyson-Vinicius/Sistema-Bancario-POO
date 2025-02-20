package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

public class Transacao {

	private LocalDateTime dataHora;
	private TipoTransacao tipo;
	private BigDecimal valor;
	private String descricao;

	public Transacao(TipoTransacao tipo, BigDecimal valor, String descricao) {
		if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Valor da transação deve ser positivo.");
		}
		if (descricao == null || descricao.trim().isEmpty()) {
			throw new IllegalArgumentException("Descrição não pode ser vazia.");
		}

		this.dataHora = LocalDateTime.now();
		this.tipo = tipo;
		this.valor = valor.setScale(2, RoundingMode.HALF_UP); // Usando RoundingMode arredondamento utilizada quando
																// você deseja controlar como os números são
																// arredondados.
		this.descricao = descricao;
	}

	public Transacao(String string, float valor2) {
		// TODO Auto-generated constructor stub
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public TipoTransacao getTipo() {
		return tipo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("###,##0.00");
		return dataHora + " - " + tipo + ": R$" + df.format(valor) + " - " + descricao;
	}
}
