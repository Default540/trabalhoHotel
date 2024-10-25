public class Quarto {
    private final String NUMERO;
    private TipoQuarto tipo;
    private float preco;


    Quarto(String numero, TipoQuarto tipo, float preco){
        this.NUMERO = numero;
        this.tipo = tipo;
        this.preco = preco;
    }

    public String getNumero() {
        return this.NUMERO;
    }

    public TipoQuarto getTipo() {
        return this.tipo;
    }

    public float getPreco() {
        return this.preco;
    }

}
