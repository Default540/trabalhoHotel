import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class SistemaReservas {
    private Usuario usuario; //usuario logado
    private ArrayList<Usuario> usuarios; //lista dos usuarios do sistema
    private ArrayList<Hotel> hoteis; //lista dos hoteis do sistema
    private ArrayList<Reserva> reservas; //lista das reservas do sistema
    private Scanner s = new Scanner(System.in);

    SistemaReservas() {
        this.usuario = null;
        this.usuarios = new ArrayList<>();
        this.hoteis = new ArrayList<>();
        this.reservas = new ArrayList<>();

        for (Usuario u : usuarios) {
            ArrayList<Hotel> hots = u.getHoteis();

            for (Hotel hot : hots) {
                this.hoteis.add(hot);
            }

        }

    }

    public void adicionarHotel() {

        if (this.usuario.getNivelAcesso() != NivelAcesso.ADMINISTRADOR)
            return;

        boolean hotelValido = false;

        while (!hotelValido) {

            hotelValido = true;

            System.out.println("Digite o nome do hotel:\n");
            String nome = s.next();

            System.out.println("Digite o endereco do hotel:\n");
            String endereco = s.next();

            for (Hotel h : hoteis) {
                if (h.getEndereco().equals(endereco) || searchHotel(nome) != null) {
                    System.out.println("Informação invalida. Tente novamente!");
                    hotelValido = false;
                }
            }
            //adiciona a classe hotel se for valida ou volta para o loop
            if (hotelValido) {
                Hotel novoHotel = new Hotel(nome, endereco);
                usuario.adicionarHotel(novoHotel);
                this.addHotel(novoHotel);
                break;
            }
        }
    }
    
    public void removerHotel(){
                
        System.out.println("Qual o nome do hotel:");
        String nome = s.next();
        
        Hotel h = searchHotel(nome);

        for (Hotel ht : usuario.getHoteis()) {
            if (h != null && ht.getNome().equals(h.getNome())) {
                h = ht;
            }
        }

        if (h != null) {
            hoteis.remove(h);
            usuario.removerHotel(h);
        }else{
            System.out.println("Hotel não encontrado");
        }
    }

    public void listarHoteis() {

        boolean todos = true;
        //mostra apenas os hoteis do administrador
        if (usuario.getNivelAcesso() == NivelAcesso.ADMINISTRADOR) {
            System.out.println("Mostrar todos? true/false");
            todos = s.nextBoolean();
        }

        System.out.println("\nHoteis:\n");

        if (todos) {
            for (Hotel h : this.hoteis) {
                System.out.println("===============================");
                System.out.println("\nNome: " + h.getNome());
                System.out.println("Endereco: " + h.getEndereco());
                System.out.println("\nQuartos:");

                
                for (Quarto q : h.getQuartos()) {
                    
                    String estaDisp =  quartoReservado(h, q)?"Não":"Sim";
                    

                    System.out.println("\nNumero: " + q.getNumero());
                    System.out.println("Tipo: " + q.getTipo());
                    System.out.println("Preco: " + q.getPreco());

                    
                    System.out.println("Esta disponivel: " + estaDisp);
                }

                System.out.println("===============================\n\n\n");
            }
        } else {
            for (Hotel h : usuario.getHoteis()) {
                System.out.println("===============================");
                System.out.println("\nNome: " + h.getNome());
                System.out.println("Endereco: " + h.getEndereco());
                System.out.println("\nQuartos:");

                for (Quarto q : h.getQuartos()) {

                    System.out.println("\nNumero: " + q.getNumero());
                    System.out.println("Tipo: " + q.getTipo());
                    System.out.println("Preco: " + q.getPreco());

                    
                    String estaDisp = quartoReservado(h, q) ? "Sim" : "Não";
                    
                    System.out.println("Esta disponivel: " + estaDisp);
                }

                System.out.println("===============================\n\n\n");
            }
        }
    }

    public void adicionarQuarto() {
        if (this.usuario.getNivelAcesso() != NivelAcesso.ADMINISTRADOR)
            return;

        Hotel hotel = null;

        while (true) {
            System.out.println("Qual o nome do Hotel:\n");
            String nomeH = s.next();

            hotel = searchHotel(nomeH);

            if (hotel == null) {
                System.out.println("Hotel não encontrado. Tente novamente!");
            } else {
                break;
            }

        }

        String numero = null;
        while (true) {

            boolean quartoValido = true;

            System.out.println("Digite o numero do quarto:\n");
            numero = s.next();

            for (Quarto q : hotel.getQuartos()) {

                if (q.getNumero().equals(numero)) {
                    System.out.println("Numero de quarto invalido. Tente novamente!");
                    quartoValido = false;
                }

            }

            if (quartoValido)
                break;

        }

        TipoQuarto t = TipoQuarto.APARTAMENTO;

        while (true) {
            boolean quartoValido = true;

            System.out.println("Qual o tipo do quarto:\n\n");
            System.out.println("1: quarto de solteiro");
            System.out.println("2: quarto duplo de solteiro");
            System.out.println("3: quarto de casal");
            System.out.println("4: dormitorio");
            System.out.println("5: apartamento\n");

            int tq = s.nextInt();

            if (tq == 1) {
                t = TipoQuarto.QUARTO_DE_SOLTEIRO;

            } else if (tq == 2) {
                t = TipoQuarto.DUPLO_SOLTEIRO;

            } else if (tq == 3) {
                t = TipoQuarto.QUARTO_CASAL;

            } else if (tq == 4) {
                t = TipoQuarto.DORMITORIO;

            } else if (tq == 5) {
                t = TipoQuarto.APARTAMENTO;

            } else {
                System.out.println("Quarto invalido. Tente novamente!\n");
                quartoValido = false;
            }

            if (quartoValido)
                break;
        }

        System.out.println("Digite o preco do quarto:\n");
        float preco = s.nextFloat();

        Quarto novoQuarto = new Quarto(numero, t, preco);
        hotel.adicionarQuarto(novoQuarto);
    }

    public void removerQuarto(){
        Hotel h = null;
        while (true) {
        
            System.out.println("Qual o nome do hotel:");
            String nome = s.next();
            
            h = searchHotel(nome);

            for (Hotel ht : usuario.getHoteis()) {
                if (h != null && ht.getNome().equals(h.getNome())) {
                    h = ht;
                }
            }

            if (h != null) {
                break;
            }else{
                System.out.println("Hotel não encontrado.");
            }
        }

        Quarto quarto = null;

        while (true) {
            System.out.println("Qual o numero do quarto");
            String numero = s.next();

            for (Quarto q : h.getQuartos()) {
                if (q.getNumero().equals(numero)) {
                    quarto = q;            
                }
            }

            if (quarto != null) {
                h.removerQuarto(quarto);
                break;
            }else{
                System.out.println("Quarto não encontrado.");
            }
        }
    }
    
    public void listarQuartosDisponiveis() {

        Hotel h;

        while (true) {
            System.out.println("Qual o nome do Hotel:\n");
            String nomeH = s.next();

            h = searchHotel(nomeH);

            if (h == null) {
                System.out.println("Hotel não encontrado. Tente novamente!");
            } else
                break;
        }

        System.out.println("Quartos:\n");
        for (Quarto q : h.getQuartos()) {

            if (!quartoReservado(h, q)) {
                System.out.println("\nNumero: " + q.getNumero());
                System.out.println("Tipo: " + q.getTipo());
                System.out.println("Preco: " + q.getPreco());
                System.out.println("Esta disponivel: Sim");
            }
        }
    }

    public void fazerReserva() {


        Hotel hotel = null;

        while (true) {
            System.out.println("\n\nQual o nome do hotel:");
            String nomeHotel = s.next();

            if (usuario.getNivelAcesso() == NivelAcesso.ADMINISTRADOR) {
                hotel = searchHotel(nomeHotel, usuario.getHoteis());
            } else {
                hotel = searchHotel(nomeHotel);
            }

            if (hotel == null) {
                System.out.println("Hotel não encontrado. Tente novamente!");
            } else
                break;
        }

        Quarto quarto = null;

        while (true) {
            System.out.println("\n\nQual o numero do quarto:");
            String numeroQuarto = s.next();

            for (Quarto q : hotel.getQuartos()) {
                if (q.getNumero().equals(numeroQuarto)) {
                    quarto = q;
                }
            }
            
            if (quarto == null || quartoReservado(hotel, quarto)) {
                System.out.println("Quarto não encontrado ou indisponivel. Tente novamente!");
            } else
                break;
        }

        Usuario user = null;

        while (true) {

            if (usuario.getNivelAcesso() == NivelAcesso.ADMINISTRADOR) {
                System.out.println("\n\nQual o email do usuario:");

            } else if (usuario.getNivelAcesso() == NivelAcesso.CLIENTE) {
                System.out.println("\n\nQual o seu email:");
            }

            String emailUsuario = s.next();

            
            for (Usuario u : usuarios) {
                if (u.getNivelAcesso() == NivelAcesso.CLIENTE && u.getEmail().equals(emailUsuario)) {
                    user = u;
                }
            }

            if (user == null) {
                System.out.println("Clinte não encontrado. Tente novamente!");
            } else
                break;
        }

        LocalDate dataInicial = null;
        LocalDate dataFinal = null;

        while (true) {
            LocalDate dataAgora = LocalDate.now();

            System.out.println("Qual a data inicial: dd/mm/yyyy");
            String dataInicialString = s.next();

            dataInicial = LocalDate.parse(dataInicialString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            System.out.println("Qual a data de saida: dd/mm/yyyy");
            String dataFinalString = s.next();

            dataFinal = LocalDate.parse(dataFinalString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            if (dataAgora.isAfter(dataInicial) || dataAgora.isAfter(dataFinal) || dataInicial.isAfter(dataFinal)) {
                System.out.println("Data invalida! Tente novamente");
            } else
                break;
        }

        Reserva r = new Reserva(user, hotel, quarto, dataInicial, dataFinal);

        this.addReserva(r);
        user.fazerReserva(r);
    }

    public void cancelarReserva(){

        Hotel hotel = null;

        while (true) {
            System.out.println("\n\n Qual o nome do hotel:");
            String nomeHotel = s.next();

            if (usuario.getNivelAcesso() == NivelAcesso.ADMINISTRADOR) {
                //procura os hoteis do administrador
                hotel = searchHotel(nomeHotel, usuario.getHoteis());
            } else {
                hotel = searchHotel(nomeHotel);
            }

            if (hotel == null) {
                System.out.println("Hotel não encontrado. Tente novamente!");
            } else
                break;
        }

        Quarto quarto = null;

        while (true) {
            System.out.println("\n\n Qual o numero do quarto:");
            String numeroQuarto = s.next();

            for (Quarto q : hotel.getQuartos()) {
                if (q.getNumero().equals(numeroQuarto)) {
                    quarto = q;
                }
            }
            
            if (quarto == null || quartoReservado(hotel, quarto)) {
                System.out.println("Quarto não encontrado. Tente novamente!");
            } else
                break;
        }

        Usuario user = null;

        while (true) {

            //caso seja administrador ele reserva para um cliente
            if (usuario.getNivelAcesso() == NivelAcesso.ADMINISTRADOR) {
                System.out.println("Qual o email do usuario:");
                String email = s.next();
                
                for (Usuario u : usuarios) {
                    if(u.getNivelAcesso() == NivelAcesso.CLIENTE && u.getEmail().equals(email)){
                        user = u;
                    }
                }
                if (user == null) {
                    System.out.println("Usuario não encontrado. Tente novamente!");
                }else break;
            }else if(usuario.getNivelAcesso() == NivelAcesso.CLIENTE){
                user = this.usuario;
            }
        }



        Reserva reserv = null;
        
        for (Reserva r : reservas) {
            if (r.getHotel().getNome().equals(hotel.getNome()) && r.getQuarto().getNumero().equals(quarto.getNumero()) && r.getUsuario().getEmail().equals(user.getEmail())) {
                reserv = r;
            }
        }

        if (reserv == null) System.out.println("Reserva não encontrado. Tente novamente!");
        else usuario.cancelarReserva(reserv);
    }

    public void listarReservasCliente(){
        

        //só o administrador vê as listas dos clientes
        if (usuario.getNivelAcesso() != NivelAcesso.ADMINISTRADOR) return;

        Usuario user = null;

        while (true) {
            System.out.println("Qual o email do usuario:");
            String email = s.next();    

            for (Usuario u : usuarios) {
                if (u.getNivelAcesso() == NivelAcesso.CLIENTE && u.getEmail().equals(email)) {
                    user = u;            
                }
            }
            if (user == null) {
                System.out.println("Usuario não encontrado. Tente novamente!");
            }else break;
        }


        for (Reserva r : reservas) {
            for (Hotel h : usuario.getHoteis()) {
                if (r.getUsuario().getEmail().equals(user.getEmail()) && h.getNome().equals(r.getHotel().getNome())) {
                
                    System.out.println("===============================");
                    System.out.println("\nHotel:\n\n");
                    System.out.println("\nNome: " + r.getHotel().getNome());
                    System.out.println("Endereco: " + r.getHotel().getEndereco());
                    System.out.println("\nQuartos:");
    
                    Quarto q = r.getQuarto();
    
                        
    
                    System.out.println("\nNumero: " + q.getNumero());
                    System.out.println("Tipo: " + q.getTipo());
                    System.out.println("Preco: " + q.getPreco());
    
                        
                    System.out.println("Esta disponivel: Não");
                    
                    System.out.println("\nData inicial: "+ r.getDataCheckIn());
                    System.out.println("\nData final: "+ r.getDataCheckOut());
                    System.out.println("\nStatus: " + r.getReservaStatus());
    
                    System.out.println("===============================\n\n\n");
    
                }    
            }
            
        }
        
    }

    public ArrayList<Usuario> getUsuarios() {
        return this.usuarios;
    }

    public void setUsuarios(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario user) {
        this.usuario = user;
    }

    public void addHotel(Hotel hotel) {
        this.hoteis.add(hotel);
    }

    //procura o usuario com o nome
    private Hotel searchHotel(String nome) {

        for (Hotel h : this.hoteis) {

            if (h.getNome().equals(nome)) {
                return h;
            }
        }

        return null;
    }

    private boolean quartoReservado(Hotel h, Quarto q){

        boolean estaDisp = false; 
        for (Reserva r : this.reservas) {
            if (r.getReservaStatus() == ReservaStatus.RESERVADO && r.getQuarto().getNumero().equals(q.getNumero())) {
                estaDisp = true;        
            }
        }
        return estaDisp;
    }

    private Hotel searchHotel(String nome, ArrayList<Hotel> hs) {

        for (Hotel h : hs) {

            if (h.getNome().equals(nome)) {
                return h;
            }
        }

        return null;
    }

    public void addReserva(Reserva r){
        this.reservas.add(r);
    }

    public void close(){
        this.s.close();
    }
}