import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GUITelaPrincipal extends JFrame{
    private JList<String> groupList;
    private JTextField passwordField;
    private JTextField sizeField;
    private JButton generateButton;
    private JButton addButton;
    private JButton groupButton;
    private DefaultListModel<String> passwordListModel;
    private DefaultListModel<String> allGroupListModel;


    public GUITelaPrincipal(String email) {

        setTitle("Página Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));

        JLabel groupLabel = new JLabel("Grupos do Usuário:");
        // mudar para listar grupos do usuario usando email :)
        String[] groups = {"Grupo A", "Grupo B", "Grupo C"};
        groupList = new JList<>(groups);
        JScrollPane groupScroll = new JScrollPane(groupList);
        // listar todos os grupos
        groupButton = new JButton("Todos os Grupos");

        JPanel groupPanel = new JPanel(new BorderLayout());
        groupPanel.add(groupLabel, BorderLayout.NORTH);
        groupPanel.add(groupScroll, BorderLayout.CENTER);
        groupPanel.add(groupButton, BorderLayout.SOUTH);

        JLabel passwordLabel = new JLabel("Tamanho Senha:");
        sizeField = new JTextField(4);
        passwordField = new JTextField(25);
        generateButton = new JButton("Gerar Senha");

        groupList.addListSelectionListener(e ->{
            if (!e.getValueIsAdjusting()) {
                int index = groupList.getSelectedIndex();
                if (index != -1) {

                    String grupoSelecionado = groupList.getSelectedValue();
                    TelaDetalhesGrupo(grupoSelecionado);
                }
            }
        });

        groupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                TelaListaGrupos();
            }
        });

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            if(sizeField.getText().isBlank()){
                JOptionPane.showMessageDialog(null,"Insira um tamanho para a senha gerada!","Tamanho",JOptionPane.ERROR_MESSAGE);
            }else {
                int tamanho = Integer.parseInt(sizeField.getText());
                if (tamanho < 8) {
                    JOptionPane.showMessageDialog(null, "Senha gerada deve ter pelo menos 8 caracteres!", "Tamanho", JOptionPane.ERROR_MESSAGE);
                } else {
                    String rand = GeradorSenhas.gerarSenha(tamanho);
                    passwordField.setText(rand);
                }
            }
            }
        });

        JPanel passwordPanel = new JPanel();
        passwordPanel.add(passwordLabel);
        passwordPanel.add(sizeField);
        passwordPanel.add(passwordField);
        passwordPanel.add(generateButton);

        JLabel savedPasswordLabel = new JLabel("Senhas Armazenadas:");
        passwordListModel = new DefaultListModel<>();
        JList<String> passwordList = new JList<>(passwordListModel);
        JScrollPane passwordScroll = new JScrollPane(passwordList);
        adicionarSenhas(passwordListModel, email);
        addButton = new JButton("Adicionar");

        passwordList.addListSelectionListener(e ->{
            if (!e.getValueIsAdjusting()) {
                int index = passwordList.getSelectedIndex();
                if (index != -1) {

                    String senhaSelecionada = passwordListModel.getElementAt(index);

                    String[] parts = senhaSelecionada.split(" \\| ");
                    String idStr = parts[0].replace("ID: ", "").trim();
                    int idSenha = Integer.parseInt(idStr);

                    TelaDetalhesSenha(idSenha);
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                janelaCadastroSenha(email);
            }
        });

        JPanel savedPasswordPanel = new JPanel(new BorderLayout());
        savedPasswordPanel.add(savedPasswordLabel, BorderLayout.NORTH);
        savedPasswordPanel.add(passwordScroll, BorderLayout.CENTER);
        savedPasswordPanel.add(addButton, BorderLayout.SOUTH);

        mainPanel.add(groupPanel, BorderLayout.WEST);
        mainPanel.add(passwordPanel, BorderLayout.NORTH);
        mainPanel.add(savedPasswordPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void janelaCadastroSenha(String email) {

        JDialog dialog = new JDialog(this, "Cadastrar Senha", true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new GridLayout(4, 2, 10, 10));


        JLabel appLabel = new JLabel("Aplicação:");
        JTextField appField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email/Usuário:");
        JTextField emailField = new JTextField(20);

        JLabel senhaLabel = new JLabel("Senha:");
        JTextField senhaField = new JTextField(20);

        JButton saveButton = new JButton("Salvar");


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeAplic = appField.getText();
                String login = emailField.getText();
                String senhaAplic = senhaField.getText();

                if(nomeAplic.isBlank()){
                    JOptionPane.showMessageDialog(null,"Nome da aplicação está vazio!","Nome Aplicação",JOptionPane.ERROR_MESSAGE);
                }else if(login.isBlank()){
                    JOptionPane.showMessageDialog(null,"Login da aplicação está vazio!","Login Aplicação",JOptionPane.ERROR_MESSAGE);
                }else if(senhaAplic.isBlank()){
                    JOptionPane.showMessageDialog(null,"Senha da aplicação está vazio!","Senha Aplicação",JOptionPane.ERROR_MESSAGE);
                }else{
                    TelaSenha telaSenha = new TelaSenha();
                    int id = telaSenha.inserirSenha(email,new Senha(nomeAplic,login,senhaAplic));

                    if(id > 0){
                        String entrada = "ID: " + id + " | " + "Aplicação: " + nomeAplic;
                        passwordListModel.addElement(entrada);
                        dialog.dispose();
                    }else{
                        JOptionPane.showMessageDialog(null,"Erro ao adicionar senha!","Erro",JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });

        dialogPanel.add(appLabel);
        dialogPanel.add(appField);
        dialogPanel.add(emailLabel);
        dialogPanel.add(emailField);
        dialogPanel.add(senhaLabel);
        dialogPanel.add(senhaField);
        dialogPanel.add(saveButton);
        dialog.add(dialogPanel);
        dialog.setVisible(true);
    }

    public void adicionarSenhas(DefaultListModel<String> passwordListModel, String email){
        List<Integer> lista = TelaSenha.listarIds(email);
        String entrada;
        for(int i = 0; i < lista.size(); i++) {
            entrada = "ID: " + lista.get(i) + " | " + "Aplicação: " + TelaSenha.retornaSenha(lista.get(i)).getNomeAplicacao();
            passwordListModel.addElement(entrada);
        }
    }

    public void TelaDetalhesSenha(int idSenha) {
        Senha senha = TelaSenha.retornaSenha(idSenha);
        JDialog dialog = new JDialog(this, "Detalhes da Aplicação", true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new GridLayout(4, 2, 10, 10));

        JTextField appField = new JTextField(senha.getNomeAplicacao());
        appField.setEditable(false);

        JTextField emailField = new JTextField(senha.getEmailLogin());
        emailField.setEditable(false);

        JTextField senhaField = new JTextField(senha.getSenha());
        senhaField.setEditable(false);

        dialogPanel.add(new JLabel("Aplicação:"));
        dialogPanel.add(appField);

        dialogPanel.add(new JLabel("Email/Usuário:"));
        dialogPanel.add(emailField);

        dialogPanel.add(new JLabel("Senha:"));
        dialogPanel.add(senhaField);


        dialog.add(dialogPanel);
        dialog.setVisible(true);
    }

    public void TelaDetalhesGrupo(String nomeGrupo){
        JDialog dialog = new JDialog(this, "Detalhes do Grupo", true);
        dialog.setSize(500, 300);
        dialog.setLocationRelativeTo(this);

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new GridLayout(4, 2, 10, 10));

        JTextField groupNameField = new JTextField(nomeGrupo);
        groupNameField.setEditable(false);

        //adicionar lista de usuario/senhas do grupo

        dialogPanel.add(new JLabel("Nome Grupo:"));
        dialogPanel.add(groupNameField);


        dialog.add(dialogPanel);
        dialog.setVisible(true);
    }

    public void TelaListaGrupos(){
        JDialog dialog = new JDialog(this, "Todos os Grupos", true);
        dialog.setSize(300, 800);
        dialog.setLocationRelativeTo(this);

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel allGroupsLabel = new JLabel("Grupos:");
        allGroupListModel = new DefaultListModel<>();
        JList<String> allGroupList = new JList<>(allGroupListModel);
        JScrollPane allGroupScroll = new JScrollPane(allGroupList);
        // mudar para todos os grupos do BD
        allGroupListModel.addElement("Grupo A");
        allGroupListModel.addElement("Grupo B");
        allGroupListModel.addElement("Grupo C");

        allGroupList.addListSelectionListener(e ->{
            if (!e.getValueIsAdjusting()) {
                String grupoSelecinado = allGroupList.getSelectedValue();
                int result = JOptionPane.showConfirmDialog(dialog,"Deseja se juntar ao " + grupoSelecinado + "?","Confirmação",JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    // Adicionar lógica para usuário se juntar ao grupo
                    JOptionPane.showMessageDialog(dialog, "Você se juntou ao " + grupoSelecinado + "!");
                }
            }
        });
        JPanel allGroupsPanel = new JPanel(new BorderLayout());
        allGroupsPanel.add(allGroupsLabel);
        allGroupsPanel.add(allGroupScroll);

        dialogPanel.add(allGroupsPanel, BorderLayout.CENTER);
        dialog.add(dialogPanel);
        dialog.setVisible(true);
    }

}
