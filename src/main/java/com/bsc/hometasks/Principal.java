package com.bsc.hometasks;

import com.bsc.hometasks.db.dao.*;
import com.bsc.hometasks.pojo.*;

import java.util.List;

public class Principal {
    private static void testaUsuario(){

        Usuario usuario = new Usuario("Roque017","Guilherme","1997,02,08",
                "masculino",0,"48991100802",
                "1233545","roquee@email","sem teto");

        UsuarioDAO dao = new UsuarioDAO();
        dao.criaUsuario(usuario);
        System.out.println(dao.buscaUsuario("Roque017"));

        usuario.setGenero("femininno");
        usuario.setIdUsuario("Roque017");
        System.out.println(dao.atualizaUsuario(usuario));
        System.out.println(dao.buscaUsuario("Roque017"));
        System.out.println(dao.removeUsuario("Roque017"));
        System.out.println(dao.buscaUsuario("Roque017"));

        for(Usuario u : dao.buscaUsuariosId("Roque")){
            System.out.println(u);
        }

        System.out.println("-----------------------------");

        for(Usuario u : dao.buscaUsuariosNome("Gui")){
            System.out.println(u);
        }

    }

    private static void testeCasa(){
        Casa casa = new Casa("Casa dos Roque","rua jacinto pinto",
                500,"um quarto");
        CasaDAO dao = new CasaDAO();
        casa.setIdCasa(dao.criaCasa(casa));
        System.out.println(dao.buscaCasa(casa.getIdCasa()));
        casa.setAluguel(800);
        dao.atualizaCasa(casa);
        System.out.println(dao.buscaCasa(casa.getIdCasa()));
        dao.removeCasa(casa.getIdCasa());
        System.out.println(dao.buscaCasa(casa.getIdCasa()));

        for (Casa c : dao.buscaCasasNome("CASA")){
            System.out.println(c);
        }
        System.out.println("-----------------------------");
        for (Casa c : dao.buscaCasasEndereco("rua")){
            System.out.println(c);
        }

    }

    public static void testePagamento(){
        Pagamento pagamento = new Pagamento("Roque07","Roque007",0,500,
                "2019-02-02","droga");
        PagamentoDAO dao = new PagamentoDAO();
        pagamento.setIdPagamento(dao.criaPagamento(pagamento));
        System.out.println(dao.buscaPagamento(pagamento.getIdPagamento(),
                pagamento.getIdCredor(),pagamento.getIdDevedor()));


        pagamento.setValor(900);
        dao.atualizaPagamento(pagamento);

        System.out.println(dao.buscaPagamento(pagamento.getIdPagamento(),
                pagamento.getIdCredor(),pagamento.getIdDevedor()));

        dao.removePagamento(pagamento);

        System.out.println(dao.buscaPagamento(pagamento.getIdPagamento(),
                pagamento.getIdCredor(),pagamento.getIdDevedor()));

    }

    public static void testeTarefa(){
        Tarefa tarefa = new Tarefa("nome","descricao","Roque0",
                "Roque0",  "estado", "data", 500);

        TarefaDAO dao = new TarefaDAO();
        tarefa.setIdTarefa(dao.criaTarefa(tarefa));
        System.out.println(dao.buscaTarefa(tarefa.getIdTarefa()));
        tarefa.setValor(800);
        dao.atualizaTarefa(tarefa);
        System.out.println(dao.buscaTarefa(tarefa.getIdTarefa()));
        //dao.removerTarefa(tarefa.getIdTarefa());
        System.out.println(dao.buscaTarefa(tarefa.getIdTarefa()));

        System.out.println("-------------------------");
        for (Tarefa t : dao.buscaTarefasUsuarioEstado("Roque0","aberta")){
            System.out.println(t);
        }

        System.out.println("-------------------------");
        for (Tarefa t : dao.buscaTarefasUsuarioEstado("Roque0","finalizada")){
            System.out.println(t);
        }

    }

    public static void testeRegra(){
        Regra regra = new Regra("nome","descricao",false,
                "Roque0",1,"2018-02-02",500);

        RegraDAO dao = new RegraDAO();
        regra.setIdRegra(dao.criaRegra(regra));
        System.out.println(dao.buscaRegra(regra.getIdRegra(),regra.getIdCasa()));
        regra.setDescricao("LALAALAL");
        dao.atualizaRegra(regra);
        System.out.println(dao.buscaRegra(regra.getIdRegra(),regra.getIdCasa()));
        dao.removeRegra(regra.getIdRegra(),regra.getIdCasa());
        System.out.println(dao.buscaRegra(regra.getIdRegra(),regra.getIdCasa()));
        for(Regra r : dao.buscaRegrasCasa(1)){
            System.out.println(r);
        }
    }

    public static void testeRotina(){
        RotinaDAO dao = new RotinaDAO();
        Rotina rotina = dao.buscaRotina(3);

        System.out.println(dao.buscaRotina(rotina.getIdRotina()));
        //dao.removeRotina(rotina.getIdRotina());
        //System.out.println(dao.buscaRotina(rotina.getIdRotina()));

        System.out.println("-----------------");
        List<Rotina> rotinas = dao.buscaRotinasUsuario("luluzinha");
        System.out.println("size" + rotinas.size());
        for (Rotina r : rotinas){
            System.out.println(r);
        }


    }
    public static void testeConvite() {
        UsuarioDAO daoUsuario = new UsuarioDAO();
        Usuario roque0 = daoUsuario.buscaUsuario("Roque0");
        Casa casa = new Casa("Casa dos Roque","rua jacinto pinto",
                500,"um quarto");
        CasaDAO daoCasa = new CasaDAO();
        roque0.setIdCasa(daoCasa.criaCasa(casa));
        daoUsuario.atualizaUsuario(roque0);

        Convite convite = new Convite("Roque0","Roque1",
                roque0.getIdCasa(),false,false);

        ConviteDAO dao = new ConviteDAO();

        dao.criaConvite(convite);
        System.out.println(dao.buscaConvite(convite.getIdConvidado(),
                convite.getIdConvidante(), convite.getIdCasa()));

        convite.setEstado(true);

        dao.atualizaConvite(convite);

        System.out.println(dao.buscaConvite(convite.getIdConvidado(),
                convite.getIdConvidante(), convite.getIdCasa()));

        dao.removeConvite(convite.getIdConvidado(),
                convite.getIdConvidante(), convite.getIdCasa());

        System.out.println(dao.buscaConvite(convite.getIdConvidado(),
                convite.getIdConvidante(), convite.getIdCasa()));

        System.out.println("-----------");
        for(Convite c : dao.buscaConvitesUsuario("Roque0",true)){
            System.out.println(c);
        }

    }

    public static void testeAprova(){
        Aprova aprova = new Aprova("Roque0",2,false);
        AprovaDAO dao = new AprovaDAO();
        dao.criaAprova(aprova);
        System.out.println(dao.buscaAprova("Roque0",2));
        aprova.setEstado(true);
        dao.atualizaAprova(aprova);
        System.out.println(dao.buscaAprova("Roque0",2));

        System.out.println("----------------");
        for (Aprova a : dao.buscaAprovaRegra(2)){
            System.out.println(a);
        }


    }

    public static void testeUsuarioRotina(){
        UsuarioRotina usuarioRotina = new UsuarioRotina("Roque0",1,
                "2000-02-02",true);
        UsuarioRotinaDAO dao = new UsuarioRotinaDAO();
        dao.criaUsuarioRotina(usuarioRotina);
        System.out.println(dao.buscaUsuarioRotina(usuarioRotina.getIdUsuario(),usuarioRotina.getIdRotina()));
        usuarioRotina.setEstado(false);
        dao.autualizaUsuarioRotina(usuarioRotina);
        System.out.println(dao.buscaUsuarioRotina(usuarioRotina.getIdUsuario(),usuarioRotina.getIdRotina()));

        System.out.println("--------------------");
        for(UsuarioRotina ur : dao.buscaUsuarioRotinaUsuario("Roque0")){
            System.out.println(ur);
        }

    }

    public static void testeComentario(){
        Comentario comentario = new Comentario("lalala",null,"1999-02-02",1,"Roque0");
        ComentarioDAO dao = new ComentarioDAO();
        dao.criaComentario(comentario);
        comentario.setData("1999-02-11");
        dao.atualizaComentario(comentario);
        System.out.println(dao.buscaComentario(comentario.getIdTarefa(),comentario.getIdUsuario()));

        System.out.println("--------------------");
        for (Comentario c : dao.buscaComentariosTarefa(1)){
            System.out.println(c);
        }

    }

        public static void main (String arg []){
            //testaUsuario();
            //testeCasa();
            //testePagamento();
            //testeTarefa();
            //testeRegra();
            testeRotina();
            //testeConvite();
            //testeAprova();
            //testeUsuarioRotina();
            //testeComentario();
    }
}