package br.com.udesc.mongodb;

import java.net.UnknownHostException;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import javax.swing.JOptionPane;

public class Principal {

    public static void main(String[] args) throws UnknownHostException {
        Menu();
        System.exit(0);
    }

    public static void Menu() {
        int opcao = -1;
        while (opcao != 0) {
            opcao = Integer.parseInt(JOptionPane.showInputDialog("[1] - Inserir\n"
                    + "[2] - Remover\n"
                    + "[3] - Listar\n"
                    + "[4] - Editar\n"
                    + "[5] - Pesquisar\n"
                    + "[6] - Propriedades\n"
                    + "[0] - Sair"));
            switch (opcao) {
                case 1:
                    inserir(MongoConnection.getInstance());
                    break;
                case 2:
                    excluir(MongoConnection.getInstance());
                    break;
                case 3:
                    listar(MongoConnection.getInstance());
                    break;
                case 4:
                    editar();
                    break;
                case 5:
                    pesquisar(MongoConnection.getInstance());
                    break;
                case 6:
                    propriedades(MongoConnection.getInstance());
                    break;
                case 0: 
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida");
                    break;
            }
        }
    }

    public static void listar(MongoConnection con) {
        con.getDB();
        DBCursor oFind = con.getCollection().find();
        if (oFind.count() == 0) {
            JOptionPane.showMessageDialog(null, "Coleção vazia!");
        } else {
            for (DBObject documento : oFind) {
                String id = documento.get("id").toString();
                String titulo = documento.get("titulo").toString();
                String autor = documento.get("autor").toString();
                String editora = documento.get("editora").toString();
                String paginas = documento.get("paginas").toString();
                String disponivel = documento.get("disponivel").toString();
                JOptionPane.showMessageDialog(null, "Código:  " + id + ""
                        + "\nTítulo:  " + titulo + ""
                        + "\nAutor:  " + autor + ""
                        + "\nEditora:  " + editora + ""
                        + "\nPáginas:  " + paginas + ""
                        + "\nDisponível:  " + disponivel);
            }
        }
    }

    public static void pesquisar(MongoConnection con) {
        con.getDB();
        boolean achou = false;
        String tituloBusca = JOptionPane.showInputDialog("Informe o título");
        if (tituloBusca.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Campo não pode ser vazio, verifique!", "Atenção", 2);
            return;
        }
        DBObject query = BasicDBObjectBuilder.start().add("titulo", tituloBusca).get();
        DBCursor oFind = con.getCollection().find(query);
        for (DBObject documento : oFind) {
            String tituloExiste = documento.get("titulo").toString();
            if (tituloExiste.equalsIgnoreCase((tituloBusca))) {
                String id = documento.get("id").toString();
                String titulo = documento.get("titulo").toString();
                String autor = documento.get("autor").toString();
                String editora = documento.get("editora").toString();
                String paginas = documento.get("paginas").toString();
                String disponivel = documento.get("disponivel").toString();
                JOptionPane.showMessageDialog(null, "*** Título Encontrado! ***"
                        + "\nCódigo:  " + id + ""
                        + "\nTítulo:  " + titulo + ""
                        + "\nAutor:  " + autor + ""
                        + "\nEditora:  " + editora + ""
                        + "\nPáginas:  " + paginas + ""
                        + "\nDisponível:  " + disponivel);
                achou = true;
            }
        }
        if (!achou) {
            JOptionPane.showMessageDialog(null, "Título não encontrado");
        }
    }

    public static void excluir(MongoConnection con) {
        con.getDB();
        int id = Integer.parseInt(JOptionPane.showInputDialog("Informe o código p/ remover"));
        DBObject query = BasicDBObjectBuilder.start().add("id", id).get();
        boolean achou = false;
        DBCursor oFind = con.getCollection().find(query);
        for (DBObject documento : oFind) {
            int codigoExiste = Integer.parseInt(documento.get("id").toString());
            if (codigoExiste == id) {
                con.getCollection().remove(query);
                achou = true;
            }
        }
        if (!achou) {
            JOptionPane.showMessageDialog(null, "Livro não encontrado");
        } else {
            JOptionPane.showMessageDialog(null, "Livro removido com sucesso!");
        }
    }

    public static void editar() {
        int edita = -1;
        while (edita != 0) {
            edita = Integer.parseInt(JOptionPane.showInputDialog("[1] Buscar livro p/ edição pelo código"
                                                             + "\n[0] Menu Principal"));
            if(edita == 1){
               confirmaEdicao(MongoConnection.getInstance());
            }
            if(edita != 0 && edita != 1){
               JOptionPane.showMessageDialog(null, "Opção inválida");
            }
        }        
    }

    public static void confirmaEdicao(MongoConnection con) {
        con.getDB();
        boolean achou = false;
        int id = Integer.parseInt(JOptionPane.showInputDialog("Informe o código para buscar \n"
                                                            + "ou [0] para sair: "));
        if(id == 0){
           return;
        }
        DBCursor oFind = con.getCollection().find();
        Livro oLivro = new Livro();
        for (DBObject documento : oFind) {
            int iId = Integer.parseInt(documento.get("id").toString());
            if (iId == id) {
                achou = true;
                oLivro.setId(id);
                oLivro.setTitulo(documento.get("titulo").toString());
                oLivro.setAutor(documento.get("autor").toString());
                oLivro.setEditora(documento.get("editora").toString());
                oLivro.setPaginas(Integer.parseInt(documento.get("paginas").toString()));
                oLivro.setDisponivel(documento.get("disponivel").toString());
            }
        }
        if (!achou) {
            JOptionPane.showMessageDialog(null, "Livro não encontrado");
            return;
        } else {
            int opcaoEditar = opcaoEditar(oLivro);
            if (opcaoEditar > 5 || opcaoEditar < 1) {
                JOptionPane.showMessageDialog(null, "Opção inválida!");
            } else {
                if (opcaoEditar == 1) {
                    String titulo = JOptionPane.showInputDialog("Informe novo título");
                    oLivro.setTitulo(titulo);
                }
                if (opcaoEditar == 2) {
                    String autor = JOptionPane.showInputDialog("Informe novo autor");
                    oLivro.setTitulo(autor);
                }
                if (opcaoEditar == 3) {
                    String editora = JOptionPane.showInputDialog("Informe nova editora");
                    oLivro.setAutor(editora);
                }
                if (opcaoEditar == 4) {
                    int paginas = Integer.parseInt(JOptionPane.showInputDialog("Informe número de páginas"));
                    oLivro.setPaginas(paginas);
                }
                if (opcaoEditar == 5) {
                    int iDisponivel = Integer.parseInt(JOptionPane.showInputDialog("Disponível? [1] Sim  [0] Não"));
                    String existe = "Não";
                    if (iDisponivel == 1) {
                        existe = "Sim";
                    }
                    oLivro.setDisponivel(existe);
                }
                DBObject query = BasicDBObjectBuilder.start().add("id", id).get();
                DBObject doc = createDBObject(oLivro);
                con.getCollection().update(query, doc);
                JOptionPane.showMessageDialog(null, "Livro alterado com sucesso!");
            }
        }
    }

    public static int opcaoEditar(Livro oLivro) {
        JOptionPane.showMessageDialog(null, "*** Livro encontrado ***\n" + oLivro.toString());
        int opcao = Integer.parseInt(JOptionPane.showInputDialog("[1] - Editar o titulo\n"
                + "[2] - Editar autor\n"
                + "[3] - Editar editora\n"
                + "[4] - Editar páginas\n"
                + "[5] - Disponibilidade\n"));
        return opcao;
    }

    //traz estado atual do objeto livro 
    private static DBObject createDBObject(Livro oLivro) {
        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();

        docBuilder.append("id", oLivro.getId());
        docBuilder.append("titulo", oLivro.getTitulo());
        docBuilder.append("autor", oLivro.getAutor());
        docBuilder.append("editora", oLivro.getEditora());
        docBuilder.append("paginas", oLivro.getPaginas());
        docBuilder.append("disponivel", oLivro.getDisponivel());
        return docBuilder.get();
    }

    //atribui os dados para o BD
    public static void inserir(MongoConnection con) {
        //conexão aberta no getMaxId
        Livro oLivro = createLivro(con);
        DBObject getObjeto = createDBObject(oLivro);
        con.getCollection().insert(getObjeto);
    }

    //atribui os dados para o objeto
    private static Livro createLivro(MongoConnection con) {
        Livro livro = new Livro();
        int maxId = getMaxId(con);
        livro.setId(maxId + 1);
        String sTitulo = JOptionPane.showInputDialog("Informe o Título");
        livro.setTitulo(sTitulo);
        String sAutor = JOptionPane.showInputDialog("Informe o Autor");
        livro.setAutor(sAutor);
        String sEditora = JOptionPane.showInputDialog("Informe a Editora");
        livro.setEditora(sEditora);
        int iPaginas = Integer.parseInt(JOptionPane.showInputDialog("Informe o numero de paginas"));
        livro.setPaginas(iPaginas);
        int iDisponivel = Integer.parseInt(JOptionPane.showInputDialog("Disponivel? [1] Sim [0] Não"));
        String existe = "Não";
        if (iDisponivel == 1) {
            existe = "Sim";
        }
        livro.setDisponivel(existe);
        return livro;
    }

    //garante identidade chave
    private static int getMaxId(MongoConnection con) {
        con.getDB();
        int id = 0;
        DBCursor oFind = con.getCollection().find();
        for (DBObject documento : oFind) {
            int iId = Integer.parseInt(documento.get("id").toString());
            if (iId > id) {
                id = iId;
            }
        }
        return id;
    }

    public static void propriedades(MongoConnection con) {
        con.getDB();
        JOptionPane.showMessageDialog(null, "Nome da minha base de dados: " + con.getCollection().getDB()
                + "\nMinha Coleção de dados:  " + con.getCollection().getName()
                + "\nTotal de documentos:  " + con.getCollection().getCount());
    }
}
