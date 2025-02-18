package model;

public enum TipoTransacao {
    CREDITO("Crédito"),
    DEBITO("Débito"),
    TRANSFERENCIA_CREDITO("Transferência (Crédito)"),
    TRANSFERENCIA_DEBITO("Transferência (Débito)");

    private final String descricao;

    // Construtor do Enum que armazena a descrição do tipo de transação
    TipoTransacao(String descricao) {
        this.descricao = descricao;
    }

    // Método para obter a descrição legível do tipo de transação
    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
