import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUILogin {

    private JPanel panel;

    public GUILogin() {
        JFrame frame = new JFrame("Login");
        frame.setSize(475, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new GridBagLayout());
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
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

        JLabel passLabel = new JLabel("Senha:");
        grid.gridx = 0;
        grid.gridy = 1;
        passLabel.setFont(font);
        panel.add(passLabel, grid);

        JPasswordField passText = new JPasswordField(20);
        grid.gridx = 1;
        passText.setFont(font);
        panel.add(passText, grid);

        JButton loginButton = new JButton("Login");
        grid.gridx = 1;
        grid.gridy = 2;
        loginButton.setFont(font);
        panel.add(loginButton, grid);

        JButton regButton = new JButton("Cadastro");
        grid.gridx = 0;
        regButton.setFont(font);
        panel.add(regButton, grid);

        JButton recButton = new JButton("Recuperar senha");
        grid.gridx = 0;
        grid.gridy = 3;
        grid.gridwidth = 2;
        recButton.setFont(font);
        panel.add(recButton, grid);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    TelaLogin telaLogin = new TelaLogin();
                    String email = mailText.getText();
                    String senha = new String(passText.getPassword());

                    if (!TelaLogin.validarEmail(email)) {
                        JOptionPane.showMessageDialog(panel, "Email e/ou senha inválidos!", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                        passText.setText("");
                        return;
                    }
                    if (!TelaLogin.validarSenha(senha)) {
                        JOptionPane.showMessageDialog(panel, "Email e/ou senha inválidos!", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                        passText.setText("");
                        return;
                    }

                    Criptografia criptografia = new Criptografia();
                    SecretKey chave = criptografia.geradorChave();
                    String senhaHash = TelaLogin.hashSenha(senha);
                    String senhaCriptografada = Criptografia.criptografa(senhaHash, chave);

                    if(telaLogin.funcionamentoTelaLogin(email, senhaCriptografada, chave)){
                        JOptionPane.showMessageDialog(panel, "Login realizado com sucesso!", "Login com sucesso", JOptionPane.INFORMATION_MESSAGE);
                        mailText.setText("");
                        passText.setText("");
                        new GUITelaPrincipal(email);
                        (SwingUtilities.getWindowAncestor(panel)).dispose();
                    }else{
                        JOptionPane.showMessageDialog(panel, "Email e/ou senha inválidos!", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Algo deu errado, reinicie o programa!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        recButton.addActionListener(e -> {
            new GUIRecuperacao();
            (SwingUtilities.getWindowAncestor(panel)).dispose();
        });

        regButton.addActionListener(e -> {
            new GUICadastro();
            (SwingUtilities.getWindowAncestor(panel)).dispose();
        });
    }
}