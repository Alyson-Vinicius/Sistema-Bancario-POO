package model;

import java.time.LocalDateTime;

public enum TipoTransacao {
    CREDITO("Crédito"),
    DEBITO("Débito"),
    TRANSFERENCIA_CREDITO("Transferência (Crédito)"),
    TRANSFERENCIA_DEBITO("Transferência (Débito)");

    private final String descricao;
	private LocalDateTime Year;
	private LocalDateTime MonthValue;

    // Construtor do Enum que armazena a descrição do tipo de transação
    TipoTransacao(String descricao , LocalDateTime MonthValue , LocalDateTime Year) {
        this.descricao = descricao;
        this.MonthValue = MonthValue;
        this.Year = Year;
    }


	TipoTransacao(String string) {
		this.descricao = "";
		// TODO Auto-generated constructor stub
	}


	// Método para obter a descrição legível do tipo de transação
    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}


	public LocalDateTime getYear() {
		return Year;
	}


	public void setYear(LocalDateTime year) {
		Year = year;
	}


	public LocalDateTime getMonthValue() {
		return MonthValue;
	}


	public void setMonthValue(LocalDateTime monthValue) {
		MonthValue = monthValue;
	}
	
	
}
