package exception;

public class ContaInativaException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ContaInativaException(String mensagem) {
        super(mensagem);
    }
}
