/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package janelas;
import conexao.MyConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableModel;
import java.util.Calendar;

/**
 *
 * @author ASUS     //Projeto Desenvolvido por Guilherme Martins e Hugo Oliveira
 */
public class JanelaRegReserva extends javax.swing.JInternalFrame {
   private JComboBox<String> comboQuartos;
    private JComboBox<String> comboHospedes;
    private JComboBox<Integer> comboDiaInicio;
    private JComboBox<Integer> comboMesInicio;
    private JComboBox<Integer> comboAnoInicio;
    private JComboBox<Integer> comboDiaFim;
    private JComboBox<Integer> comboMesFim;
    private JComboBox<Integer> comboAnoFim;
    private JSpinner numeroPessoas;
    private JButton btnReservar;
    private JTable tabelaReservas;
    private JButton btnEliminarReserva;

    public JanelaRegReserva() {
        initperComponents();
        setupLayout();
        carregarQuartosDisponiveis();
        carregarHospedes();
        carregarReservas();
    }

    private void initperComponents() {
        comboQuartos = new JComboBox<>();
        comboHospedes = new JComboBox<>();
        
        comboDiaInicio = new JComboBox<>(getDias());
        comboMesInicio = new JComboBox<>(getMeses());
        comboAnoInicio = new JComboBox<>(getAnos());
        comboDiaFim = new JComboBox<>(getDias());
        comboMesFim = new JComboBox<>(getMeses());
        comboAnoFim = new JComboBox<>(getAnos());
        
        numeroPessoas = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        btnReservar = new JButton("Reservar");
        tabelaReservas = new JTable();
        btnEliminarReserva = new JButton("Eliminar Reserva");

        btnReservar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reservarQuarto();
            }
            });
        
    
        btnEliminarReserva.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            eliminarReserva();
        }
    });
    }

    private void setupLayout() {
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Reserva de Quartos");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        addComponent(panel, new JLabel("Quarto:"), gbc, 0, 0);
        addComponent(panel, comboQuartos, gbc, 1, 0, 2, 1);

        addComponent(panel, new JLabel("Hóspede:"), gbc, 0, 1);
        addComponent(panel, comboHospedes, gbc, 1, 1, 2, 1);

        addComponent(panel, new JLabel("Data de Início:"), gbc, 0, 2);
        JPanel dataInicioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        dataInicioPanel.add(comboDiaInicio);
        dataInicioPanel.add(comboMesInicio);
        dataInicioPanel.add(comboAnoInicio);
        addComponent(panel, dataInicioPanel, gbc, 1, 2, 2, 1);

        addComponent(panel, new JLabel("Data de Fim:"), gbc, 0, 3);
        JPanel dataFimPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        dataFimPanel.add(comboDiaFim);
        dataFimPanel.add(comboMesFim);
        dataFimPanel.add(comboAnoFim);
        addComponent(panel, dataFimPanel, gbc, 1, 3, 2, 1);

        addComponent(panel, new JLabel("Número de Pessoas:"), gbc, 0, 4);
        addComponent(panel, numeroPessoas, gbc, 1, 4, 2, 1);

        addComponent(panel, btnReservar, gbc, 0, 5, 2, 1);
        addComponent(panel, btnEliminarReserva, gbc, 2, 5, 1, 1);
        
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        addComponent(panel, new JScrollPane(tabelaReservas), gbc, 0, 6, 3, 1);

        getContentPane().add(panel);

        pack();
    }

    private void addComponent(JPanel panel, JComponent comp, GridBagConstraints gbc, int x, int y) {
        addComponent(panel, comp, gbc, x, y, 1, 1);
    }

    private void addComponent(JPanel panel, JComponent comp, GridBagConstraints gbc, int x, int y, int width, int height) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.fill = (comp instanceof JScrollPane) ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;
        panel.add(comp, gbc);
    }

    private Integer[] getDias() {
        Integer[] dias = new Integer[31];
        for (int i = 0; i < 31; i++) {
            dias[i] = i + 1;
        }
        return dias;
    }

    private Integer[] getMeses() {
        Integer[] meses = new Integer[12];
        for (int i = 0; i < 12; i++) {
            meses[i] = i + 1;                                                       //Projeto Desenvolvido por Guilherme Martins e Hugo Oliveira
        }
        return meses;
    }

    private Integer[] getAnos() {
        Integer[] anos = new Integer[10];
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 10; i++) {
            anos[i] = anoAtual + i;
        }
        return anos;
    }

    private void carregarQuartosDisponiveis() {
        comboQuartos.removeAllItems();
        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT numero FROM quartos WHERE disponivel = true");
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                comboQuartos.addItem(rs.getString("numero"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar quartos: " + ex.getMessage());
        }
    }

    private void carregarHospedes() {
        comboHospedes.removeAllItems();
        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT id, nome FROM hospedes");
             ResultSet rs = pstmt.executeQuery()) {                                                                                                                                                             //Projeto Desenvolvido por Guilherme Martins e Hugo Oliveira

            while (rs.next()) {
                comboHospedes.addItem(rs.getString("id") + " - " + rs.getString("nome"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar hóspedes: " + ex.getMessage());
        }
    }

    private void reservarQuarto() {
        String quartoNumero = (String) comboQuartos.getSelectedItem();
        String hospedeInfo = (String) comboHospedes.getSelectedItem();
        int hospedeId = Integer.parseInt(hospedeInfo.split(" - ")[0]);
        
        String dataInicioStr = String.format("%04d-%02d-%02d", 
            comboAnoInicio.getSelectedItem(), 
            comboMesInicio.getSelectedItem(), 
            comboDiaInicio.getSelectedItem());
        String dataFimStr = String.format("%04d-%02d-%02d", 
            comboAnoFim.getSelectedItem(), 
            comboMesFim.getSelectedItem(), 
            comboDiaFim.getSelectedItem());
        
        int numPessoas = (int) numeroPessoas.getValue();

        try (Connection conn = new MyConnection().getConnection();
             PreparedStatement pstmtReserva = conn.prepareStatement("INSERT INTO reservas (quarto_numero, hospede_id, data_inicio, data_fim, numero_pessoas) VALUES (?, ?, ?, ?, ?)");
             PreparedStatement pstmtQuarto = conn.prepareStatement("UPDATE quartos SET disponivel = false WHERE numero = ?")) {

            conn.setAutoCommit(false);

            pstmtReserva.setInt(1, Integer.parseInt(quartoNumero));
            pstmtReserva.setInt(2, hospedeId);
            pstmtReserva.setDate(3, java.sql.Date.valueOf(dataInicioStr));
            pstmtReserva.setDate(4, java.sql.Date.valueOf(dataFimStr));
            pstmtReserva.setInt(5, numPessoas);
            pstmtReserva.executeUpdate();

            pstmtQuarto.setInt(1, Integer.parseInt(quartoNumero));
            pstmtQuarto.executeUpdate();

            conn.commit();
            JOptionPane.showMessageDialog(this, "Reserva realizada com sucesso!");
            limparCampos();
            carregarQuartosDisponiveis();
            carregarReservas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao fazer reserva: " + ex.getMessage());
        }
    }
    private void limparCampos() {
    comboQuartos.setSelectedIndex(0);
    comboHospedes.setSelectedIndex(0);
    
    // Resetar datas para a data atual
    Calendar hoje = Calendar.getInstance();
    comboDiaInicio.setSelectedItem(hoje.get(Calendar.DAY_OF_MONTH));
    comboMesInicio.setSelectedItem(hoje.get(Calendar.MONTH) + 1);                                                                                                                                                                                           //Projeto Desenvolvido por Guilherme Martins e Hugo Oliveira
    comboAnoInicio.setSelectedItem(hoje.get(Calendar.YEAR));
    
    comboDiaFim.setSelectedItem(hoje.get(Calendar.DAY_OF_MONTH));
    comboMesFim.setSelectedItem(hoje.get(Calendar.MONTH) + 1);
    comboAnoFim.setSelectedItem(hoje.get(Calendar.YEAR));
    
    numeroPessoas.setValue(1);
}

    private void carregarReservas() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Número do Quarto", "Hóspede", "Data Início", "Data Fim", "Nº Pessoas"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    try (Connection conn = new MyConnection().getConnection();
         PreparedStatement pstmt = conn.prepareStatement("SELECT r.id, r.quarto_numero, h.nome, r.data_inicio, r.data_fim, r.numero_pessoas FROM reservas r JOIN hospedes h ON r.hospede_id = h.id");
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("id"),
                rs.getString("quarto_numero"),
                rs.getString("nome"),
                rs.getDate("data_inicio"),
                rs.getDate("data_fim"),
                rs.getInt("numero_pessoas")
            });
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar reservas: " + ex.getMessage());
    }
    tabelaReservas.setModel(model);
    tabelaReservas.getColumnModel().getColumn(0).setMinWidth(0);
    tabelaReservas.getColumnModel().getColumn(0).setMaxWidth(0);
    tabelaReservas.getColumnModel().getColumn(0).setWidth(0);
}
    
    private void eliminarReserva() {
    int selectedRow = tabelaReservas.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Por favor, selecione uma reserva para eliminar.");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this, 
        "Tem certeza que deseja eliminar esta reserva?", 
        "Confirmar Eliminação", 
        JOptionPane.YES_NO_OPTION);
    
    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    String quartoNumero = tabelaReservas.getValueAt(selectedRow, 0).toString();
    String dataInicio = tabelaReservas.getValueAt(selectedRow, 2).toString();

    try (Connection conn = new MyConnection().getConnection();
         PreparedStatement pstmtReserva = conn.prepareStatement("DELETE FROM reservas WHERE quarto_numero = ? AND data_inicio = ?");                                                                                                            //Projeto Desenvolvido por Guilherme Martins e Hugo Oliveira
         PreparedStatement pstmtQuarto = conn.prepareStatement("UPDATE quartos SET disponivel = true WHERE numero = ?")) {

        conn.setAutoCommit(false);

        pstmtReserva.setString(1, quartoNumero);
        pstmtReserva.setDate(2, java.sql.Date.valueOf(dataInicio));
        pstmtReserva.executeUpdate();

        pstmtQuarto.setString(1, quartoNumero);
        pstmtQuarto.executeUpdate();

        conn.commit();
        JOptionPane.showMessageDialog(this, "Reserva eliminada com sucesso!");
        carregarQuartosDisponiveis();
        carregarReservas();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Erro ao eliminar reserva: " + ex.getMessage());
    }
}
   
//Projeto Desenvolvido por Guilherme Martins e Hugo Oliveira
    
    //public JanelaRegReserva() {
      // initComponents();
    //}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 274, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
