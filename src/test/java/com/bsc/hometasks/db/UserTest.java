package com.bsc.hometasks.db;

import com.bsc.hometasks.db.dao.UsuarioDAO;
import com.bsc.hometasks.pojo.Usuario;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest {
    private UsuarioDAO user = new UsuarioDAO();

    private Usuario joao = new Usuario("joao.silva","João Silva","2019-11-09",
            "Masculino",10, "99999-9999","123","joao@bsc.com","usuario");

    private Usuario maria = new Usuario("maria","Maria","2019-11-09",
            "Feminino",50, "98888-8888","456","maria@bsc.com","usuario");

    @Test
    public void registerUser(){
        assertTrue("Usuário João Silva não registrado, ", user.criaUsuario(joao));
        assertTrue("Usuário Maria não registrado", user.criaUsuario(maria));

        joao.setIdUsuario("joao.souza");
        joao.setNome("João Souza");
        assertTrue("Usuário João Souza não registrado", user.criaUsuario(joao));

    }

    @Test
    public void registerUserSameId(){
        joao.setIdUsuario("joao.silva");

        assertFalse("Usuario com id=joao.silva registrado, mas já existe",
                user.criaUsuario(joao));
        assertFalse("Usuario com id=maria registrado mas já existe",
                user.criaUsuario(maria));

    }

    @Test
    public void registerUserWithOutLogin(){
        maria.setIdUsuario("");
        assertFalse("Usuario com id em branco registrado, mas não deveria permitir",
                user.criaUsuario(maria));

        maria.setIdUsuario(null);
        assertFalse("Usuario null registrado, mas não deveria permitir",
                user.criaUsuario(maria));


    }

    @Test
    public void updateUser(){
        maria.setIdUsuario("maria");
        maria.setNome("Maria Silva");
        int result = user.atualizaUsuario(maria);
        assertTrue("Usuário não atualizado",result > 0);

    }

    @Test
    public void searchUserById(){
        Assert.assertEquals("Usuário encontrado, mas não existe no banco",
                null,user.buscaUsuario("bob"));

        Assert.assertEquals("Usuário não encontrado, mas existe no banco",
                maria.toString(),user.buscaUsuario(maria.getIdUsuario()).toString());

    }

    @Test
    public void removeUser(){
        Assert.assertEquals("Usuário removido, mas não existe no banco",
                0,user.removeUsuario("bob"));

        Assert.assertEquals("Usuário não removido, mas existe no banco",
                1,user.removeUsuario("joao.souza"));
    }

    @Test
    public void searchUsersByName(){
        maria.setIdUsuario("ana.maria");
        maria.setNome("Ana Maria");
        user.criaUsuario(maria);

        maria.setIdUsuario("maria.ana");
        maria.setNome("Maria Ana");
        user.criaUsuario(maria);

        Assert.assertEquals("Usuário(s) encontrado(s), mas não existe(m) no banco",
                new ArrayList<Usuario>(),user.buscaUsuariosNome("jos"));

        int listSize = user.buscaUsuariosNome("Maria").size();
        assertTrue("Usuário(s) não encontrado(s), mas existe(m) no banco", listSize == 4);

        listSize = user.buscaUsuariosNome("").size();
        assertTrue("Pesquisa em branco, não deveria retornar nada", listSize == 0);

    }

    @Test
    public void searchUsersById(){
        joao.setIdUsuario("joao.souza");
        joao.setNome("João Souza");
        user.criaUsuario(joao);

        joao.setIdUsuario("joao.almeida");
        joao.setNome("João Almeida");
        user.criaUsuario(joao);

        Assert.assertEquals("Usuário(s) encontrado(s), mas não existe(m) no banco",
                new ArrayList<Usuario>(),user.buscaUsuariosId("jon"));

        int listSize = user.buscaUsuariosNome("joao").size();
        assertTrue("Usuário(s) não encontrado(s), mas existe(m) no banco", listSize == 3);


    }
}
