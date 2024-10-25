import java.util.ArrayList;

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String telefone;
    private NivelAcesso nivelAcesso;
    private ArrayList<Hotel> hoteis;
    
    Usuario(int id, String nome, NivelAcesso nv){
        this.id = id;
        this.nome = nome;
        this.nivelAcesso = nv;
        this.hoteis = new ArrayList<>();
        this.email = null;
        this.telefone = null;
    }

    Usuario(int id, String nome, String email, String telefone){
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.nivelAcesso = NivelAcesso.CLIENTE;
    }

    public void adicionarHotel(Hotel hotel){
        if (this.getNivelAcesso() == NivelAcesso.ADMINISTRADOR) {
            hoteis.add(hotel);
        }
    }

    public void removerHotel(Hotel hotel){
        if (this.nivelAcesso == NivelAcesso.ADMINISTRADOR) {
            hoteis.remove(hotel);
        }
    }
    
    public void fazerReserva(Reserva reserva){
        reserva.setReservaStatus(ReservaStatus.RESERVADO);
    }
    
    public void cancelarReserva(Reserva reserva){
        reserva.setReservaStatus(ReservaStatus.CANCELADO);
    }

    public ArrayList<Hotel> getHoteis(){
        return this.hoteis;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getEmail() {
        return email;
    }
    public String getTelefone() {
        return telefone;
    }
    public NivelAcesso getNivelAcesso(){
        return nivelAcesso;
    }

}