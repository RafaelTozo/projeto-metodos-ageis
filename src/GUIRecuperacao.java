import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIRecuperacao {

    public GUIRecuperacao() {
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
        panel.removeAll();
        GridBagConstraints grid = new GridBagConstraints();
        Font font = new Font("Arial", Font.PLAIN, 14);
        grid.insets = new Insets(10, 10, 10, 10);
        grid.fill = GridBagConstraints.HORIZONTAL;

        JLabel recLabel = new JLabel("  Insira um email de uma conta cadastrada!");
        grid.gridx = 0;
        grid.gridy = 0;
        grid.gridwidth = 2;
        recLabel.setFont(font);
        panel.add(recLabel, grid);

        JLabel mailLabel = new JLabel("Email:");
        grid.gridx = 0;
        grid.gridy = 1;
        grid.gridwidth = 1;
        mailLabel.setFont(font);
        panel.add(mailLabel, grid);

        JTextField mailText = new JTextField(20);
        grid.gridx = 1;
        grid.gridy = 1;
        grid.gridwidth = 1;
        mailText.setFont(font);
        panel.add(mailText, grid);

        JButton sendButton = new JButton("Enviar");
        grid.gridx = 0;
        grid.gridy = 2;
        grid.gridwidth = 2;
        sendButton.setFont(font);
        panel.add(sendButton, grid);

        JButton returnButton = new JButton("Voltar");
        grid.gridy = 3;
        grid.gridwidth = 2;
        returnButton.setFont(font);
        panel.add(returnButton, grid);

        returnButton.addActionListener(e -> {
            new GUILogin();
            (SwingUtilities.getWindowAncestor(panel)).dispose();
        });

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    TelaRecuperacao telaRecuperacao = new TelaRecuperacao();
                    String email = mailText.getText();
                    if(!telaRecuperacao.validarEmail(email)){
                        JOptionPane.showMessageDialog(panel, "Insira um email válido!", "Email", JOptionPane.ERROR_MESSAGE);
                    }else if(!telaRecuperacao.verificaEmail(email)){
                        JOptionPane.showMessageDialog(panel, "Email não cadastrado!", "Email", JOptionPane.ERROR_MESSAGE);
                    }else {
                        String codEmail = telaRecuperacao.enviaEmail(email);
                        guiEmail(panel,email,codEmail);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Algo deu errado, reinicie o programa!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void guiEmail(JPanel panel, String email, String codEmail) {
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
                    if(codeText.getText().length() != 6 || !codeText.getText().equals(codEmail)){
                        JOptionPane.showMessageDialog(panel, "Código inválido!", "Código inválido", JOptionPane.ERROR_MESSAGE);
                    }else {
                        guiNovaSenha(panel, email);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Algo deu errado, reinicie o programa!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void guiNovaSenha(JPanel panel, String email) {
        panel.removeAll();
        GridBagConstraints grid = new GridBagConstraints();
        Font font = new Font("Arial", Font.PLAIN, 14);
        grid.insets = new Insets(10, 10, 10, 10);
        grid.fill = GridBagConstraints.HORIZONTAL;

        JLabel newPassLabel = new JLabel("Crie uma nova senha!");
        grid.gridx = 1;
        grid.gridy = 0;
        grid.gridwidth = 2;
        newPassLabel.setFont(font);
        panel.add(newPassLabel, grid);

        JLabel passLabel = new JLabel("Nova Senha:");
        grid.gridx = 0;
        grid.gridy = 1;
        grid.gridwidth = 1;
        passLabel.setFont(font);
        panel.add(passLabel, grid);

        JPasswordField passText = new JPasswordField(20);
        grid.gridx = 1;
        grid.gridy = 1;
        grid.gridwidth = 1;
        passText.setFont(font);
        panel.add(passText, grid);

        JLabel confirmPassLabel = new JLabel("Repita a Senha:");
        grid.gridx = 0;
        grid.gridy = 2;
        grid.gridwidth = 1;
        confirmPassLabel.setFont(font);
        panel.add(confirmPassLabel, grid);

        JPasswordField confirmPassText = new JPasswordField(20);
        grid.gridx = 1;
        grid.gridy = 2;
        grid.gridwidth = 1;
        confirmPassText.setFont(font);
        panel.add(confirmPassText, grid);

        JButton resetButton = new JButton("Salvar Nova Senha");
        grid.gridx = 0;
        grid.gridy = 3;
        grid.gridwidth = 2;
        resetButton.setFont(font);
        panel.add(resetButton, grid);

        JButton returnButton = new JButton("Voltar");
        grid.gridx = 0;
        grid.gridy = 4;
        grid.gridwidth = 2;
        returnButton.setFont(font);
        panel.add(returnButton, grid);

        panel.revalidate();
        panel.repaint();

        returnButton.addActionListener(e -> {
            new GUIRecuperacao();
            (SwingUtilities.getWindowAncestor(panel)).dispose();
        });

        resetButton.addActionListener(e -> {
            String novaSenha = new String(passText.getPassword());
            String confirmarSenha = new String(confirmPassText.getPassword());
            TelaRecuperacao telaRecuperacao = new TelaRecuperacao();

            if(!telaRecuperacao.validarSenha(novaSenha)) {
                JOptionPane.showMessageDialog(panel, "A senha precisa ter no mínimo 8 caracteres!", "Erro", JOptionPane.ERROR_MESSAGE);
            }else if (!novaSenha.equals(confirmarSenha)) {
                JOptionPane.showMessageDialog(panel, "As senhas não correspondem!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                Criptografia criptografia = new Criptografia();
                SecretKey chave = criptografia.geradorChave();
                String senhaCriptografada = Criptografia.criptografa(novaSenha, chave);

                if(telaRecuperacao.funcionamentoTelaRecuperacao(email, senhaCriptografada, chave)) {
                    JOptionPane.showMessageDialog(panel, "Senha alterada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    new GUILogin();
                }else{
                    JOptionPane.showMessageDialog(panel, "Ocorreu um erro tente novamente!", "Erro", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        panel.revalidate();
        panel.repaint();
    }
}
