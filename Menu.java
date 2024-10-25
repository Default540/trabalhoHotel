import java.util.Scanner;

public class Menu {

    private SistemaReservas sistema;
    private Scanner scanner;

    Menu() {
        this.sistema = new SistemaReservas();
        this.scanner = new Scanner(System.in);
    }

    // menu de login
    public void menuLogin() {
        boolean on = true;

        // switch para criação de conta e login
        while (on) {

            System.out.println("\nSelecione uma das opções:\n");

            // exibir o menu quado estiver logado
            if (sistema.getUsuario() != null) {
                System.out.println("1: Exibir Menu");
            }

            System.out.println("2: Criar conta");

            // permitir login quando existir ao menos um usuario
            if (sistema.getUsuarios().size() > 0) {
                System.out.println("3: Login");
            }

            System.out.println("4: Sair");

            Scanner scanner = new Scanner(System.in);
            int input = scanner.nextInt();

            switch (input) {

                case 1:
                    if (sistema.getUsuario() != null) {
                        menuPrincipal();
                    } else {
                        System.out.println("Opção inválida. Tente novamente.");
                    }
                    break;

                case 2:
                    criarConta();
                    break;

                case 3:

                    if (sistema.getUsuarios().size() > 0) {
                        login();
                    } else {
                        System.out.println("Opção inválida. Tente novamente.");
                    }

                    break;
                case 4:
                    sistema.close();
                    scanner.close();
                    on = false;

                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    
    public void criarConta() {

        boolean contaValida = false;

        while (!contaValida) {

            contaValida = true;
            NivelAcesso nAcesso = null;
            boolean tipoValido = false;

            // informa o tipo de conta
            while (!tipoValido) {

                tipoValido = true;

                System.out.println("Qual o tipo da conta:");
                System.out.println("1: Cliente");
                System.out.println("2: Administrador");
                int tc = scanner.nextInt();

                if (tc == 1) {
                    nAcesso = NivelAcesso.CLIENTE;
                    break;
                } else if (tc == 2) {
                    nAcesso = NivelAcesso.ADMINISTRADOR;
                    break;
                } else {
                    System.out.println("Tipo invalido. Tente novamente!");
                    tipoValido = false;
                }
            }

            System.out.println("Digite o seu nome:");
            String nome = scanner.next();

            // o cliente precisa de informações como o email e telefone
            if (nAcesso == NivelAcesso.CLIENTE) {

                System.out.println("Digite o telefone do cliente:");
                String telefone = scanner.next();

                System.out.println("Digite o email do cliente:");
                String email = scanner.next();

                for (Usuario u : this.sistema.getUsuarios()) {

                    if ((u.getEmail() != null && u.getEmail().equals(email))
                            || (u.getTelefone() != null && u.getTelefone().equals(telefone))) {
                        System.out.println("Usuario invalido. Tente novamente!");
                        contaValida = false;
                    }
                }

                if (contaValida == true) {
                    Usuario newUser = new Usuario(sistema.getUsuarios().size(), nome, email,
                            telefone);
                    sistema.setUsuarios(newUser);
                }

            } else if (nAcesso == NivelAcesso.ADMINISTRADOR) {
                Usuario newAdmin = new Usuario(sistema.getUsuarios().size(), nome, nAcesso);
                sistema.setUsuarios(newAdmin);
            }

        }
    }

    // faz login como cliente
    public void login() {

        NivelAcesso n = null;

        while (true) {
            System.out.println("Qual o tipo de conta:");
            System.out.println("1: Administrador");
            System.out.println("2: Cliente");

            int input = scanner.nextInt();

            if (input == 1) {
                n = NivelAcesso.ADMINISTRADOR;
                break;
            } else if (input == 2) {
                n = NivelAcesso.CLIENTE;
                break;
            } else {
                System.out.println("Opção invalida. Tente novamente!");
            }
        }

        if (n == NivelAcesso.CLIENTE) {
            boolean loginValido = false;

            while (!loginValido) {

                System.out.println("Digite o seu nome:");
                String nome = scanner.next();
                System.out.println("Digite o seu email:");
                String email = scanner.next();

                for (Usuario u : sistema.getUsuarios()) {

                    if ((u.getNome() != null && u.getNome().equals(nome))
                            && (u.getEmail() != null && u.getEmail().equals(email))) {
                        sistema.setUsuario(u);
                        loginValido = true;
                    }

                }

                if (!loginValido)
                    System.out.println("Informação invalida. Tente novamente!");
            }
        } else if (n == NivelAcesso.ADMINISTRADOR) {
            boolean loginValido = false;

            while (!loginValido) {

                System.out.println("Digite o id do administrador:");
                int id = scanner.nextInt();
                System.out.println("Digite o nome do administrador:");
                String nome = scanner.next();

                for (Usuario u : sistema.getUsuarios()) {
                    if (u.getNome().equals(nome) && u.getId() == id) {
                        sistema.setUsuario(u);
                        loginValido = true;
                    }
                }

                if (!loginValido)
                    System.out.println("Informação invalida. Tente novamente!");
            }
        }

    }

    // principais opções para o gerenciamento do hotel
    public void menuPrincipal() {

        boolean on = true;

        while (on) {

            System.out.println("\nSelecione uma das opções:\n");
            System.out.println(" 1: Listar todos os hoteis");
            System.out.println(" 2: Listar Quartos Disponíveis em um Hotel");
            System.out.println(" 3: Fazer Reserva");
            System.out.println(" 4: Cancelar Reserva");
            System.out.println(" 5: Sair");

            // mostra as opções para o administrador
            if (sistema.getUsuario() != null && sistema.getUsuario().getNivelAcesso() == NivelAcesso.ADMINISTRADOR) {
                System.out.println(" 6: Adicionar um hotel");
                System.out.println(" 7: Remover um hotel");
                System.out.println(" 8: Adicionar um quarto a um hotel");
                System.out.println(" 9: Remover um quarto de um hotel");
                System.out.println("10: Listar Reservas de um Cliente\n");
            }

            int input = scanner.nextInt();
            System.out.println();
            switch (input) {

                case 1:
                    sistema.listarHoteis();

                    break;

                case 2:
                    sistema.listarQuartosDisponiveis();
                    break;

                case 3:
                    sistema.fazerReserva();
                    break;

                case 4:
                    sistema.cancelarReserva();
                    break;

                case 5:
                    on = false;
                    break;

                case 6:
                    if (sistema.getUsuario().getNivelAcesso() == NivelAcesso.ADMINISTRADOR) {
                        sistema.adicionarHotel();
                    } else {
                        System.out.println("Opção inválida. Tente novamente.");
                    }
                    break;

                case 7:
                    if (sistema.getUsuario().getNivelAcesso() == NivelAcesso.ADMINISTRADOR) {
                        sistema.removerHotel();

                    } else {
                        System.out.println("Opção inválida. Tente novamente.");
                    }

                    break;

                case 8:
                    if (sistema.getUsuario().getNivelAcesso() == NivelAcesso.ADMINISTRADOR) {
                        sistema.adicionarQuarto();
                    } else {
                        System.out.println("Opção inválida. Tente novamente.");
                    }

                    break;

                case 9:
                    if (sistema.getUsuario().getNivelAcesso() == NivelAcesso.ADMINISTRADOR) {
                        sistema.removerQuarto();
                    } else {
                        System.out.println("Opção inválida. Tente novamente.");
                    }
                    break;

                case 10:
                    if (sistema.getUsuario().getNivelAcesso() == NivelAcesso.ADMINISTRADOR) {
                        sistema.listarReservasCliente();
                    } else {
                        System.out.println("Opção inválida. Tente novamente.");
                    }
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }
}
