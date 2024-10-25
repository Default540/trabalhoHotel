import java.util.ArrayList;

/**
 * Hotel
 */
public class Hotel {

    private String nome;
    private String endereco;
    private ArrayList<Quarto> quartos;

    Hotel(String nome, String endereco){
        this.nome = nome;
        this.endereco = endereco;
        this.quartos = new ArrayList<>();
    }

    public void adicionarQuarto(Quarto quarto){
        this.quartos.add(quarto);
    }

    public void removerQuarto(Quarto quarto){
        this.quartos.remove(quarto);
    }

    public ArrayList<Quarto> getQuartos(){
        return this.quartos;
    }

    public String getNome(){
        return this.nome;
    }
    public String getEndereco(){
        return this.endereco;
    }
}