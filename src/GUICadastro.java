import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUICadastro {

    public GUICadastro() {
        JFrame frame = new JFrame("Cadastro");
        frame.setSize(475, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        guiCadastro(panel);

        frame.setVisible(true);
    }

    private void guiCadastro(JPanel panel) {
        GridBagConstraints grid = new GridBagConstraints();
        Font font = new Font("Arial", Font.PLAIN, 14);
        grid.insets = new Insets(10, 10, 10, 10);
        grid.fill = GridBagConstraints.HORIZONTAL;

        JLabel mailLabel = new JLabel("Email:");
        grid.gridx = 0;
        grid.gridy = 0;
        mailLabel.setFont(font);
        panel.add(mailLabel, grid);

        JTextField mailText = new JTextField(20);
        grid.gridx = 1;
        mailText.setFont(font);
        panel.add(mailText, grid);

        JLabel userLabel = new JLabel("Nome de Usuário:");
        grid.gridx = 0;
        grid.gridy = 1;
        userLabel.setFont(font);
        panel.add(userLabel, grid);

        JTextField userText = new JTextField(20);
        grid.gridx = 1;
        userText.setFont(font);
        panel.add(userText, grid);

        JLabel passLabel = new JLabel("Senha:");
        grid.gridx = 0;
        grid.gridy = 2;
        passLabel.setFont(font);
        panel.add(passLabel, grid);

        JPasswordField passText = new JPasswordField(20);
        grid.gridx = 1;
        passText.setFont(font);
        panel.add(passText, grid);

        JLabel confLabel = new JLabel("Confirme a senha:");
        grid.gridx = 0;
        grid.gridy = 3;
        confLabel.setFont(font);
        panel.add(confLabel, grid);

        JPasswordField confText = new JPasswordField(20);
        grid.gridx = 1;
        confText.setFont(font);
        panel.add(confText, grid);

        JButton regButton = new JButton("Cadastrar");
        grid.gridx = 0;
        grid.gridy = 4;
        grid.gridwidth = 2;
        regButton.setFont(font);
        panel.add(regButton, grid);

        JButton returnButton = new JButton("Voltar");
        grid.gridy = 5;
        returnButton.setFont(font);
        panel.add(returnButton, grid);

        returnButton.addActionListener(e -> {
            new GUILogin();
            (SwingUtilities.getWindowAncestor(panel)).dispose();
        });

        regButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    TelaCadastro telaCadastro = new TelaCadastro();
                    FuncoesMail funcoesMail = new FuncoesMail();
                    String email = mailText.getText();
                    String nome = userText.getText();
                    String senha = new String(passText.getPassword());
                    String senhaConfirm = new String(confText.getPassword());

                    if (!TelaCadastro.validarEmail(email)) {
                        JOptionPane.showMessageDialog(panel, "Email inválido!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                        passText.setText("");
                        confText.setText("");
                        return;
                    }
                    if (!TelaCadastro.validarSenha(senha)) {
                        JOptionPane.showMessageDialog(panel, "Senha inválida. Deve conter no mínimo 8 caracteres!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                        passText.setText("");
                        confText.setText("");
                        return;
                    }
                    if (!senha.equals(senhaConfirm)) {
                        JOptionPane.showMessageDialog(panel, "As senhas devem ser iguais!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                        passText.setText("");
                        confText.setText("");
                        return;
                    }
                    if (!TelaCadastro.validarUsuario(nome)) {
                        JOptionPane.showMessageDialog(panel, "Nome de usuário deve ter pelo menos 4 caracteres!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                        passText.setText("");
                        confText.setText("");
                        return;
                    }

                    Criptografia criptografia = new Criptografia();
                    SecretKey chave = criptografia.geradorChave();

                    String senhaCriptografada = Criptografia.criptografa(senha, chave);

                    if(!telaCadastro.verificaEmail(email)){
                        String codEmail = funcoesMail.enviarEmail(email,"Código Cadastro");
                        JOptionPane.showMessageDialog(panel, "Email enviado para confimação!", "Email", JOptionPane.INFORMATION_MESSAGE);
                        guiEmail(panel, nome, email, senhaCriptografada, chave, codEmail);
                    }else{
                        JOptionPane.showMessageDialog(panel, "Erro no cadastro, verifique se o email já está cadastrado!", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Algo deu errado, reinicie o programa!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void guiEmail(JPanel panel, String nome, String email, String senhaCriptografada, SecretKey chave, String codEmail) {
        panel.removeAll();
        GridBagConstraints grid = new GridBagConstraints();
        Font font = new Font("Arial", Font.PLAIN, 14);
        grid.insets = new Insets(10, 10, 10, 10);
        grid.fill = GridBagConstraints.HORIZONTAL;

        JLabel mailLabel = new JLabel("         Código enviado por email!");
        grid.gridx = 0;
        grid.gridy = 0;
        grid.gridwidth = 3;
        mailLabel.setFont(font);
        panel.add(mailLabel, grid);

        JLabel codeLabel = new JLabel("Código:");
        grid.gridx = 0;
        grid.gridy = 1;
        grid.gridwidth = 1;
        codeLabel.setFont(font);
        panel.add(codeLabel, grid);

        JTextField codeText = new JTextField(15);
        grid.gridx = 1;
        grid.gridy = 1;
        grid.gridwidth = 1;
        codeText.setFont(font);
        panel.add(codeText, grid);

        JButton codeButton = new JButton("Validar");
        grid.gridx = 0;
        grid.gridy = 2;
        grid.gridwidth = 3;
        codeButton.setFont(font);
        panel.add(codeButton, grid);

        JButton returnButton = new JButton("Voltar");
        grid.gridx = 0;
        grid.gridy = 3;
        grid.gridwidth = 3;
        returnButton.setFont(font);
        panel.add(returnButton, grid);

        panel.revalidate();
        panel.repaint();

        returnButton.addActionListener(e -> {
            new GUIRecuperacao();
            (SwingUtilities.getWindowAncestor(panel)).dispose();
        });

        codeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    TelaCadastro telaCadastro = new TelaCadastro();
                    if(codeText.getText().length() != 6 || !codeText.getText().equals(codEmail)){
                        JOptionPane.showMessageDialog(panel, "Código inválido!", "Código inválido", JOptionPane.ERROR_MESSAGE);
                    }else {
                        if (telaCadastro.funcionamentoTelaCadastro(nome, email, senhaCriptografada, chave)) {
                            JOptionPane.showMessageDialog(panel, "Cadastro realizado com sucesso!", "Cadastrado", JOptionPane.INFORMATION_MESSAGE);
                            new GUILogin();
                            SwingUtilities.getWindowAncestor(panel).dispose();
                        } else {
                            JOptionPane.showMessageDialog(panel, "Erro no cadastro, verifique se o email já está cadastrado!", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Algo deu errado, reinicie o programa!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}
