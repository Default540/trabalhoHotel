import java.time.LocalDate;

public class Reserva {
    private Usuario usuario;
    private Hotel hotel;
    private Quarto quarto;
    private LocalDate dataCheckIn;
    private LocalDate dataCheckOut;
    private ReservaStatus reservaStatus;

    Reserva(Usuario u, Hotel h, Quarto q, LocalDate dataInicial, LocalDate dataFinal){
        this.usuario = u;
        this.hotel = h;
        this.quarto = q;
        this.dataCheckIn = dataInicial;
        this.dataCheckOut = dataFinal;
        this.reservaStatus = ReservaStatus.RESERVADO;
    }

    public void setReservaStatus(ReservaStatus rs){
        this.reservaStatus = rs;
    }

    public ReservaStatus getReservaStatus(){
        return reservaStatus;
    }

    public Quarto getQuarto(){
        return this.quarto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Hotel getHotel() {
        return hotel;
    }
    public LocalDate getDataCheckIn() {
        return dataCheckIn;
    }
    public LocalDate getDataCheckOut() {
        return dataCheckOut;
    }
}
