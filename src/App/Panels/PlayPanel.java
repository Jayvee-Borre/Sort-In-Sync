/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package App.Panels;
import Lib.Note;
import Lib.SongLoader;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import javax.sound.sampled.FloatControl;
import Lib.Options;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.Timer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 *
 * @author Jayvee
 */
public class PlayPanel extends javax.swing.JPanel {
    // Power Ups
    private boolean fertilizerIsActive;
    private int fertilizerCount = 0;
    private boolean matShieldIsActive;
    private int matShieldCount = 0;
    private int activeShieldCharges = 0;
    private boolean shouldPurge;
    private int purgeCount = 0;
    private boolean shouldIncinerate;
    private int incinerateCount = 0;
    private long incineratorEndTime = 0;
    
    private final int MAX_BIN_CAPACITY = 10;
    private int[] binCapacity = {0,0,0,0};
    private int currentKey = 0; // this should represent UDLR
    private int spaceHitTimes;
    private int spaceHitCount = 0;
    private long pauseStartTime = 0;
    
    // Health
    private int currentHealth = 100;
    private float healthChangeModifier = 0.1f;
    private int healthChangeFactor = 10;
    
    // Points
    private int totalPoints = 0;
    private int currentCombo = 0;
    private int maxCombo = 0;
    
    // Timer and Notes
    private Options options;
    private Timer dt;
    private Timer infoTimer;
    private long startTime;
    private List<Note> notes;
    private int currentNoteIndex = 0;
    private int HIT_WINDOW = 300;
    private JComponent masterPanel;
    private Clip songClip;
    private final ImageIcon[] bioAssets = {
        new ImageIcon(getClass().getResource("/Assets/wastes/bio_trash/apple_core.png")),
        new ImageIcon(getClass().getResource("/Assets/wastes/bio_trash/withered_leaf.png")),
        new ImageIcon(getClass().getResource("/Assets/wastes/bio_trash/eggshells.png")),
        new ImageIcon(getClass().getResource("/Assets/wastes/bio_trash/fish_bone.png")),
        new ImageIcon(getClass().getResource("/Assets/wastes/bio_trash/banana_peel.png"))
        
    };
    private final ImageIcon[] recycleAssets = {
        new ImageIcon(getClass().getResource("/Assets/wastes/recyclable_trash/Aluminum_Can.png")),
        new ImageIcon(getClass().getResource("/Assets/wastes/recyclable_trash/Glass_Jars_and_Bottles.png")),
        new ImageIcon(getClass().getResource("/Assets/wastes/recyclable_trash/Newspaper.png")),
        new ImageIcon(getClass().getResource("/Assets/wastes/recyclable_trash/Plastic_Detergent_Bottle.png")),
        new ImageIcon(getClass().getResource("/Assets/wastes/recyclable_trash/Water_Bottle.png"))
    };
    private final ImageIcon[] hazardAssets = {
        new ImageIcon(getClass().getResource("/Assets/wastes/hazardous_trash/AA_Battery.png")),
        new ImageIcon(getClass().getResource("/Assets/wastes/hazardous_trash/Fluorescent_Lightbulb.png")),
        new ImageIcon(getClass().getResource("/Assets/wastes/hazardous_trash/Insecticide_Aerosol_Can.png")),
    };
    private final ImageIcon[] residualAssets = {
       new ImageIcon(getClass().getResource("/Assets/wastes/hazardous_trash/Prescription_Pill_Bottle.png")) // Switch to hazard this is only for placeholder
    };
    
    private final ImageIcon[] binAssets = {
        new ImageIcon(getClass().getResource("/Assets/bins/biodegradable_bin.png")),
        new ImageIcon(getClass().getResource("/Assets/bins/hazardous_bin.png")),
        new ImageIcon(getClass().getResource("/Assets/bins/recyclable_bin.png")),
        new ImageIcon(getClass().getResource("/Assets/bins/residual_bin.png"))
    };
    private Services.Database database;
    private String currentLevelName; // Remembers what level is being played

    public void setDatabase(Services.Database database) {
        this.database = database;
    }
    
    private void saveScore() {
        if (database != null && options != null && options.getCurrentUserId() != Options.GUEST) {
            int songId = database.getSongIdByName(currentLevelName);
            if (songId != -1) {
                database.recordScore(options.getCurrentUserId(), songId, totalPoints, maxCombo);
                System.out.println("[DB]: Score saved successfully.");
            }
        }
    }
    
    public PlayPanel() {
        initComponents();
        
        this.setFocusable(true);
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                PlayPanel.this.requestFocusInWindow();
            }
        });
    }
    
    public void setOptions(Options options) {
        this.options = options;
    }
    
    public void setMasterPanel(JComponent masterPanel) {
        this.masterPanel = masterPanel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        goBackPanel = new javax.swing.JPanel();
        goBackBtn = new App.Components.CustomButton();
        gameplayPanel = new javax.swing.JPanel();
        comboPanel = new javax.swing.JPanel();
        hpPanel = new javax.swing.JPanel();
        healthLabelTitle = new App.Components.CustomLabel();
        healthLabelText = new App.Components.CustomLabel();
        comboScorePanel = new javax.swing.JPanel();
        comboLabelTitle = new App.Components.CustomLabel();
        comboLabelScore = new App.Components.CustomLabel();
        totalScorePanel = new javax.swing.JPanel();
        comboLabelTitle1 = new App.Components.CustomLabel();
        totalScoreLabel = new App.Components.CustomLabel();
        binCapacityPanel = new javax.swing.JPanel();
        bincapLabelTitle = new App.Components.CustomLabel();
        jPanel2 = new javax.swing.JPanel();
        bincapLabelText = new App.Components.CustomLabel();
        powerupPanel = new javax.swing.JPanel();
        powerup1Panel = new javax.swing.JPanel();
        powerUpOneTitle = new javax.swing.JLabel();
        powerUpOneText = new javax.swing.JLabel();
        powerup2Panel = new javax.swing.JPanel();
        powerUpTwoTitle = new javax.swing.JLabel();
        powerUpTwoText = new javax.swing.JLabel();
        powerup3Panel = new javax.swing.JPanel();
        powerUpThreeTitle = new javax.swing.JLabel();
        powerUpThreeText = new javax.swing.JLabel();
        powerup4Panel = new javax.swing.JPanel();
        powerUpFourTitle = new javax.swing.JLabel();
        powerUpFourText = new javax.swing.JLabel();
        binPanel = new javax.swing.JPanel();
        binIcon = new javax.swing.JLabel();
        mainGameplayPanel = new javax.swing.JPanel();
        hitboxPanel = new javax.swing.JPanel();
        upcomingPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        currentPanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        textInfoPanel = new javax.swing.JPanel();
        textInfoLabel = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel1.setText("jLabel1");

        setBackground(new java.awt.Color(199, 36, 44));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());

        goBackPanel.setBackground(new java.awt.Color(199, 36, 44));
        goBackPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        goBackBtn.setText("return to menu");
        goBackBtn.setFont(new java.awt.Font("Segoe UI", 1, 8)); // NOI18N
        goBackBtn.addActionListener(this::goBackBtnActionPerformed);
        goBackPanel.add(goBackBtn);

        add(goBackPanel, java.awt.BorderLayout.NORTH);

        gameplayPanel.setBackground(new java.awt.Color(199, 36, 44));
        gameplayPanel.setLayout(new java.awt.BorderLayout());

        comboPanel.setBackground(new java.awt.Color(199, 36, 44));
        comboPanel.setLayout(new java.awt.GridLayout(4, 1));

        hpPanel.setBackground(new java.awt.Color(199, 36, 44));
        hpPanel.setLayout(new java.awt.BorderLayout());

        healthLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        healthLabelTitle.setText("HP");
        healthLabelTitle.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        hpPanel.add(healthLabelTitle, java.awt.BorderLayout.NORTH);

        healthLabelText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        healthLabelText.setText("100");
        healthLabelText.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        hpPanel.add(healthLabelText, java.awt.BorderLayout.CENTER);

        comboPanel.add(hpPanel);

        comboScorePanel.setBackground(new java.awt.Color(199, 36, 44));
        comboScorePanel.setLayout(new java.awt.BorderLayout());

        comboLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        comboLabelTitle.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        comboScorePanel.add(comboLabelTitle, java.awt.BorderLayout.NORTH);

        comboLabelScore.setBackground(new java.awt.Color(199, 36, 44));
        comboLabelScore.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        comboLabelScore.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        comboScorePanel.add(comboLabelScore, java.awt.BorderLayout.CENTER);

        comboPanel.add(comboScorePanel);

        totalScorePanel.setBackground(new java.awt.Color(199, 36, 44));
        totalScorePanel.setLayout(new java.awt.BorderLayout());

        comboLabelTitle1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        comboLabelTitle1.setText("SCORE");
        comboLabelTitle1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        totalScorePanel.add(comboLabelTitle1, java.awt.BorderLayout.NORTH);

        totalScoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalScoreLabel.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        totalScorePanel.add(totalScoreLabel, java.awt.BorderLayout.CENTER);

        comboPanel.add(totalScorePanel);

        binCapacityPanel.setBackground(new java.awt.Color(199, 36, 44));
        binCapacityPanel.setLayout(new java.awt.BorderLayout());

        bincapLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bincapLabelTitle.setText("CAPACITY");
        bincapLabelTitle.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        binCapacityPanel.add(bincapLabelTitle, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(199, 36, 44));
        jPanel2.setLayout(new java.awt.BorderLayout());

        bincapLabelText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bincapLabelText.setText("0/10");
        bincapLabelText.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jPanel2.add(bincapLabelText, java.awt.BorderLayout.CENTER);

        binCapacityPanel.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        comboPanel.add(binCapacityPanel);

        gameplayPanel.add(comboPanel, java.awt.BorderLayout.LINE_START);

        powerupPanel.setBackground(new java.awt.Color(199, 36, 44));
        powerupPanel.setLayout(new java.awt.GridLayout(4, 1));

        powerup1Panel.setBackground(new java.awt.Color(199, 36, 44));
        powerup1Panel.setLayout(new java.awt.BorderLayout());

        powerUpOneTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        powerUpOneTitle.setText("Fertilizer Boost");
        powerup1Panel.add(powerUpOneTitle, java.awt.BorderLayout.NORTH);

        powerUpOneText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        powerUpOneText.setText("0x");
        powerup1Panel.add(powerUpOneText, java.awt.BorderLayout.CENTER);

        powerupPanel.add(powerup1Panel);

        powerup2Panel.setBackground(new java.awt.Color(199, 36, 44));
        powerup2Panel.setLayout(new java.awt.BorderLayout());

        powerUpTwoTitle.setText("Material Shield");
        powerup2Panel.add(powerUpTwoTitle, java.awt.BorderLayout.NORTH);

        powerUpTwoText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        powerUpTwoText.setText("0x");
        powerup2Panel.add(powerUpTwoText, java.awt.BorderLayout.CENTER);

        powerupPanel.add(powerup2Panel);

        powerup3Panel.setBackground(new java.awt.Color(199, 36, 44));
        powerup3Panel.setLayout(new java.awt.BorderLayout());

        powerUpThreeTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        powerUpThreeTitle.setText("System Purge");
        powerup3Panel.add(powerUpThreeTitle, java.awt.BorderLayout.NORTH);

        powerUpThreeText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        powerUpThreeText.setText("0x");
        powerup3Panel.add(powerUpThreeText, java.awt.BorderLayout.CENTER);

        powerupPanel.add(powerup3Panel);

        powerup4Panel.setBackground(new java.awt.Color(199, 36, 44));
        powerup4Panel.setLayout(new java.awt.BorderLayout());

        powerUpFourTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        powerUpFourTitle.setText("Incinerator");
        powerup4Panel.add(powerUpFourTitle, java.awt.BorderLayout.NORTH);

        powerUpFourText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        powerUpFourText.setText("0x");
        powerup4Panel.add(powerUpFourText, java.awt.BorderLayout.CENTER);

        powerupPanel.add(powerup4Panel);

        gameplayPanel.add(powerupPanel, java.awt.BorderLayout.LINE_END);

        binPanel.setBackground(new java.awt.Color(199, 36, 44));
        binPanel.add(binIcon);

        gameplayPanel.add(binPanel, java.awt.BorderLayout.PAGE_END);

        mainGameplayPanel.setBackground(new java.awt.Color(199, 36, 44));

        hitboxPanel.setBackground(new java.awt.Color(199, 36, 44));
        hitboxPanel.setLayout(new java.awt.GridLayout(3, 1));

        upcomingPanel.setBackground(new java.awt.Color(199, 36, 44));
        upcomingPanel.setLayout(new java.awt.BorderLayout());

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        upcomingPanel.add(jLabel6, java.awt.BorderLayout.CENTER);

        hitboxPanel.add(upcomingPanel);

        currentPanel.setBackground(new java.awt.Color(199, 36, 44));
        currentPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 51)));
        currentPanel.setLayout(new java.awt.BorderLayout());

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currentPanel.add(jLabel7, java.awt.BorderLayout.CENTER);

        hitboxPanel.add(currentPanel);

        textInfoPanel.setBackground(new java.awt.Color(199, 36, 44));
        textInfoPanel.setLayout(new java.awt.BorderLayout());

        textInfoLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        textInfoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textInfoPanel.add(textInfoLabel, java.awt.BorderLayout.NORTH);

        hitboxPanel.add(textInfoPanel);

        mainGameplayPanel.add(hitboxPanel);

        gameplayPanel.add(mainGameplayPanel, java.awt.BorderLayout.CENTER);

        add(gameplayPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Stop timer/song if they are still running and go back to level select
    private void goBackBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goBackBtnActionPerformed
        endGame(true);
        swapCard(masterPanel, "levelPanel");
    }//GEN-LAST:event_goBackBtnActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (currentHealth <= 0) {
            endGame(true);
            javax.swing.JOptionPane.showMessageDialog(PlayPanel.this, "Game Over!");
            swapCard(masterPanel, "levelPanel");
            return;
        }
        
        int key = evt.getKeyCode();
        if (dt != null && !dt.isRunning()) {
            if (key == KeyEvent.VK_SPACE) {
                if (spaceHitCount + 1 >= spaceHitTimes) {
                    emptyTheBin();
                    spaceHitCount = 0;
                } else {
                    spaceHitCount++;
                    textInfoLabel.setText("BIN FULL! MASH SPACEBAR " + (spaceHitTimes - spaceHitCount) + " TIMES!");
                }
            }
            return;
        }
        
        
        String pressedKey = "";
        switch (key) {
            // Gameplay Keys
            case KeyEvent.VK_UP: 
                binIcon.setIcon(binAssets[1]);
                pressedKey = "U";
                currentKey = 0;
                break;
            case KeyEvent.VK_DOWN: 
                binIcon.setIcon(binAssets[2]);
                pressedKey = "D";
                currentKey = 1;
                break;
            case KeyEvent.VK_LEFT: 
                binIcon.setIcon(binAssets[0]);
                pressedKey = "L";
                currentKey = 2;
                break;
            case KeyEvent.VK_RIGHT: 
                binIcon.setIcon(binAssets[3]);
                pressedKey = "R";
                currentKey = 3;
                break;
            // Power Ups Use
            case KeyEvent.VK_Q:
                if (fertilizerCount > 0) {
                    fertilizerCount--;
                    powerUpOneText.setText(fertilizerCount + "x");
                    currentHealth = Math.min(100, currentHealth + 30); // Restores a chunk of HP
                    healthLabelText.setText(Integer.toString(currentHealth));
                    System.out.println("[POWERUP]: Fertilizer Boost used!");
                    showInfoText("Fertilizer Used: +30 HP");
                }
                break;
            case KeyEvent.VK_W:
                if (matShieldCount > 0) {
                    matShieldCount--;
                    powerUpTwoText.setText(matShieldCount + "x");
                    activeShieldCharges += 2; // Grants 2 Shield Charges
                    matShieldIsActive = true;
                    System.out.println("[POWERUP]: Material Shield active!");
                    showInfoText("Shield Active: 2 Charges");
                }
                break;
            case KeyEvent.VK_E:
                if (purgeCount > 0) {
                    purgeCount--;
                    powerUpThreeText.setText(purgeCount + "x");
                    
                    // Instantly clears the next 3 upcoming notes for max points
                    int notesToClear = Math.min(3, notes != null ? notes.size() - currentNoteIndex : 0);
                    for(int i = 0; i < notesToClear; i++) {
                        currentCombo++;
                        if (currentCombo > maxCombo) {
                            maxCombo = currentCombo;
                        }
                        int pts = 300 + (300 * currentCombo);
                        totalPoints += (shouldIncinerate ? pts * 2 : pts);
                        currentNoteIndex++;
                    }
                    totalScoreLabel.setText(Integer.toString(totalPoints));
                    updateConveyor();
                    System.out.println("[POWERUP]: System Purge used!");
                    showInfoText("System Purged: Upcoming notes cleared");
                }
                break;
            case KeyEvent.VK_R:
                if (incinerateCount > 0) {
                    incinerateCount--;
                    powerUpFourText.setText(incinerateCount + "x");
                    shouldIncinerate = true;
                    incineratorEndTime = System.currentTimeMillis() + 10000; // 10 seconds
                    System.out.println("[POWERUP]: Incinerator active for 10s");
                    showInfoText("Incinerator Active: 2x Points for 10s");
                }
                break; 
            default: return;
        }
        
        // If the key is not empty, notes is not yet finished
        if (!pressedKey.isEmpty() && notes != null) {
            if (currentNoteIndex < notes.size()) {
                long currentSongTime = System.currentTimeMillis() - startTime;
                Note currentNote = notes.get(currentNoteIndex);
                long diff = currentSongTime - currentNote.timeMs;
                
                if (Math.abs(diff) < HIT_WINDOW) { // calculate difference between note hit and hit window
                    if (currentNote.keys.contains(pressedKey) && binCapacity[currentKey] <= MAX_BIN_CAPACITY) {
                        System.out.println("[HIT]: Correct bin was pressed -> " + Math.abs(diff));
                        binCapacity[currentKey]++;
                        currentCombo++;
                        if (currentCombo > maxCombo) {
                            maxCombo = currentCombo;
                        }
                        bincapLabelText.setText(Integer.toString(binCapacity[currentKey]) + "/" + Integer.toString(MAX_BIN_CAPACITY));
                        
                        int multiplier = shouldIncinerate ? 2 : 1; // if powerup is active
                        totalPoints = totalPoints + ((300 + (300 * currentCombo)) * multiplier);
                        totalScoreLabel.setText(Integer.toString(totalPoints));
                    } else {
                        if (matShieldIsActive && activeShieldCharges > 0) {
                            activeShieldCharges--;
                            System.out.println("[SHIELD]: Mistake absorbed");
                            showInfoText("Shield Absorbed Mistake! (" + activeShieldCharges + " left)");
                            if (activeShieldCharges == 0) matShieldIsActive = false;
                        } else {
                            System.out.println("[MISS]: Wrong Bin");
                            showInfoText("Miss: Wrong Bin");
                            resetCombo();
                            loseHealth();
                        }
                    }
                    currentNoteIndex++;
                    updateConveyor();
                } else if (diff < -HIT_WINDOW) { // early hit
                    System.out.println("[MISS] Too early");
                    currentNoteIndex++;
                    if (matShieldIsActive && activeShieldCharges > 0) {
                        activeShieldCharges--;
                        System.out.println("[SHIELD]: Mistake absorbed! Charges left: " + activeShieldCharges);
                        showInfoText("Shield Absorbed Mistake (" + activeShieldCharges + " left)");
                        if (activeShieldCharges == 0) matShieldIsActive = false;
                    } else {
                        System.out.println("[MISS]: Wrong Bin / Missed timing");
                        showInfoText("Miss: Wrong Bin");
                        resetCombo();
                        loseHealth();
                    }
                    updateConveyor();
                }
            }
        }
    }//GEN-LAST:event_formKeyPressed

    private void emptyTheBin() {
        binCapacity[currentKey] = 0;
        bincapLabelText.setText(Integer.toString(binCapacity[currentKey]) + "/" + Integer.toString(MAX_BIN_CAPACITY));
        switch (currentKey) {
            case 0:
                purgeCount++;
                powerUpThreeText.setText(Integer.toString(purgeCount) + "x");
                break;
            case 1:
                matShieldCount++;
                powerUpTwoText.setText(Integer.toString(matShieldCount) + "x");
                break;
            case 2:
                fertilizerCount++;
                powerUpOneText.setText(Integer.toString(fertilizerCount) + "x");
                break;
            case 3:
                incinerateCount++;
                powerUpFourText.setText(Integer.toString(incinerateCount) + "x");
                break;
        }
        
        long pauseDuration = System.currentTimeMillis() - pauseStartTime;
        startTime += pauseDuration;
        
        textInfoLabel.setText("");
        songClip.start();
        dt.start();
    }
    
    private void showInfoText(String message) {
        // Do not overwrite the text if the minigame is currently paused
        if (dt != null && !dt.isRunning()) {
            return; 
        }
        
        textInfoLabel.setText(message);
        
        // If a previous message timer is still running restart it
        if (infoTimer != null && infoTimer.isRunning()) {
            infoTimer.stop();
        }
        
        infoTimer = new Timer(1500, (ActionEvent e) -> {
            // Only clear the text if the game isnt paused
            if (dt != null && dt.isRunning()) {
                textInfoLabel.setText("");
            }
        });
        infoTimer.setRepeats(false); // Only run once
        infoTimer.start();
    }
    
    private void checkDifficulty(String difficulty) {
        System.out.println("[Check]: Current difficulty: " + difficulty);
        switch (difficulty) {
            case "Easy": HIT_WINDOW = 600; break;
            case "Normal": HIT_WINDOW = 400; break;
            case "Hard": HIT_WINDOW = 200; break;
        }
    }
    
    private void endGame(boolean manualEndIt) {
        if (manualEndIt) {
            if (dt != null && dt.isRunning()) dt.stop();
            
            if (songClip != null && songClip.isRunning()) {
                songClip.stop();
                songClip.close();
            }
        }
    }
    
    private void swapCard(JComponent comp, String name) {
        CardLayout card = (CardLayout) comp.getLayout();
        card.show(comp, name);
    }
    
    private void loseHealth() {
        int healthVal = Integer.parseInt(healthLabelText.getText());
        currentHealth = healthVal - ( (int) (healthChangeModifier * (float) healthChangeFactor));
        healthChangeModifier += 0.05f;
        healthLabelText.setText(Integer.toString(currentHealth));
    }
    
    private void resetCombo() {
        currentCombo = 0;
        comboLabelTitle.setText("");
        comboLabelScore.setText("");
    }
    
    private void resetLevelStats() {
        currentHealth = 100;
        healthChangeModifier = 0.1f;
        healthLabelText.setText(Integer.toString(currentHealth));
        currentCombo = 0;
        maxCombo = 0;
        currentNoteIndex = 0;
        totalPoints = 0;
        spaceHitCount = 0;
        comboLabelTitle.setText("");
        comboLabelScore.setText("");
        totalScoreLabel.setText("0");
        
        fertilizerCount = 0;
        matShieldCount = 0;
        activeShieldCharges = 0;
        purgeCount = 0;
        incinerateCount = 0;
        
        fertilizerIsActive = false;
        matShieldIsActive = false;
        shouldPurge = false;
        shouldIncinerate = false;
        
        // Reset UI text for Power-Ups
        powerUpOneText.setText("0x");
        powerUpTwoText.setText("0x");
        powerUpThreeText.setText("0x");
        powerUpFourText.setText("0x");
        
        // Reset Bin Capacities
        for (int i = 0; i < binCapacity.length; i++) {
            binCapacity[i] = 0;
        }
        bincapLabelText.setText("0/" + MAX_BIN_CAPACITY);
        
        // Randomize the bin icon start
        binIcon.setIcon(binAssets[(int) (Math.random() * 4)]);
    }
    
    public void startGame(String levelName, String difficulty) {
        currentLevelName = levelName;
        checkDifficulty(difficulty);
        SongLoader song = new SongLoader();
        song.loadChart("src/Charts/" + levelName + "/" + levelName + "_" + difficulty + ".sr");
        notes = song.getNotes();
        assignImagesToNotes(notes);
        
        resetLevelStats();
        updateConveyor();
        
        loadSong(levelName);
        startTime = System.currentTimeMillis();
        songClip.start();
        dt = new Timer(16, (ActionEvent e) -> {
            if (binCapacity[currentKey] >= MAX_BIN_CAPACITY) {
                // Stop music
                songClip.stop();
                dt.stop();
                
                // Randomize Spacebar hit
                pauseStartTime = System.currentTimeMillis();
                spaceHitTimes = (int) (Math.random() * 4) + 2;
                
                // Info text
                textInfoLabel.setText("BIN FULL MASH SPACEBAR " + spaceHitTimes + " TIMES!");
            } else {
                if (shouldIncinerate && System.currentTimeMillis() > incineratorEndTime) {
                    shouldIncinerate = false;
                    showInfoText("Incinerator Ended.");
                    System.out.println("[POWERUP]: Incinerator ended.");
                }

                if (currentCombo > 0) {
                    comboLabelTitle.setText("COMBO");
                    comboLabelScore.setText("x"+ Integer.toString(currentCombo));
                }
                long currentSongTime = System.currentTimeMillis() - startTime;

                if (notes != null && currentNoteIndex < notes.size()) {
                    Note currentNote = notes.get(currentNoteIndex);
                    long timeToNote = currentNote.timeMs - currentSongTime;

                    if (currentHealth <= 0) {
                        endGame(true);
                        javax.swing.JOptionPane.showMessageDialog(PlayPanel.this, "Game Over!");
                        swapCard(masterPanel, "levelPanel");
                        return;
                    }

                    // visual indicator for time to hit
                    if (Math.abs(timeToNote) <= HIT_WINDOW) {
                        currentPanel.setBackground(new java.awt.Color(50, 205, 50)); 
                    } else {
                        currentPanel.setBackground(new java.awt.Color(199, 36, 44)); 
                    }

                    // Player missed
                    if (currentSongTime > currentNote.timeMs + HIT_WINDOW) {
                        if (matShieldIsActive && activeShieldCharges > 0) {
                            activeShieldCharges--;
                            showInfoText("Shield Absorbed Mistake! (" + activeShieldCharges + " left)");
                            if (activeShieldCharges == 0) matShieldIsActive = false;
                        } else {
                            System.out.println("[MISS]: Player missed");
                            showInfoText("Miss: Note Dropped!");
                            resetCombo();
                            loseHealth();
                        }

                        currentNoteIndex++;
                        currentPanel.setBackground(new java.awt.Color(199, 36, 44)); // Reset on miss
                        updateConveyor();
                    }
                } else if (notes != null && currentNoteIndex >= notes.size()) { //  No more notes to load
                    System.out.println("[Finish]: Level finished.");
                    dt.stop();
                    if (songClip != null) {
                        songClip.stop();
                        songClip.close();
                    }

                    saveScore();
                    javax.swing.JOptionPane.showMessageDialog(this, "Level Complete!\nFinal Score: " + totalPoints);

                    swapCard(masterPanel, "levelPanel"); 
                }
            }
        });
        
        dt.start();
    }
    
    private void loadSong(String levelName) {
        try {
            System.out.println(levelName);
            File audioFile = new File("src/Songs/" + levelName + ".wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            songClip = AudioSystem.getClip();
            songClip.open(audioStream);
            
            if (options != null && songClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) songClip.getControl(FloatControl.Type.MASTER_GAIN);
                int volume = options.getVolume();
                
                if (volume == 0) {
                    gainControl.setValue(gainControl.getMinimum());
                } else {
                    float dB = (float) (Math.log10(volume / 100.0) * 20.0);
                    gainControl.setValue(dB);
                }
            }
        } catch (Exception e) {
            System.out.println("[Error]: Could not load song.");
            e.printStackTrace();
        }
    }
    
    // Assign the images to the notes
    private void assignImagesToNotes(List<Note> activeNotes) {
        for (Note n : activeNotes) {
            if (n.keys.contains("L") && bioAssets.length > 0) {
                n.icon = bioAssets[(int) (Math.random() * bioAssets.length)];
            } else if (n.keys.contains("D") && recycleAssets.length > 0) {
                n.icon = recycleAssets[(int) (Math.random() * recycleAssets.length)];
            } else if (n.keys.contains("U") && hazardAssets.length > 0) {
                n.icon = hazardAssets[(int) (Math.random() * hazardAssets.length)];
            } else if (n.keys.contains("R") && residualAssets.length > 0) {
                n.icon = residualAssets[(int) (Math.random() * residualAssets.length)];
            }
        }
    }

    // Update the current conveyor item
    private void updateConveyor() {
        if (notes == null || currentNoteIndex >= notes.size()) {
            jLabel7.setIcon(null); // Clear Current
            jLabel6.setIcon(null); // Clear Upcoming
            return;
        }

        // Set Current Trash
        jLabel7.setIcon(notes.get(currentNoteIndex).icon);

        // Set Upcoming Trash (50% Opacity)
        if (currentNoteIndex + 1 < notes.size()) {
            ImageIcon nextIcon = notes.get(currentNoteIndex + 1).icon;
            jLabel6.setIcon(makeTranslucent(nextIcon, 0.5f));
        } else {
            jLabel6.setIcon(null);
        }
    }

    // Make icon 50% opacity
    private ImageIcon makeTranslucent(ImageIcon original, float alpha) {
        if (original == null) return null;
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(
            original.getIconWidth(), original.getIconHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2d = img.createGraphics();
        g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, alpha));
        original.paintIcon(null, g2d, 0, 0);
        g2d.dispose();
        return new ImageIcon(img);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel binCapacityPanel;
    private javax.swing.JLabel binIcon;
    private javax.swing.JPanel binPanel;
    private App.Components.CustomLabel bincapLabelText;
    private App.Components.CustomLabel bincapLabelTitle;
    private App.Components.CustomLabel comboLabelScore;
    private App.Components.CustomLabel comboLabelTitle;
    private App.Components.CustomLabel comboLabelTitle1;
    private javax.swing.JPanel comboPanel;
    private javax.swing.JPanel comboScorePanel;
    private javax.swing.JPanel currentPanel;
    private javax.swing.JPanel gameplayPanel;
    private App.Components.CustomButton goBackBtn;
    private javax.swing.JPanel goBackPanel;
    private App.Components.CustomLabel healthLabelText;
    private App.Components.CustomLabel healthLabelTitle;
    private javax.swing.JPanel hitboxPanel;
    private javax.swing.JPanel hpPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel mainGameplayPanel;
    private javax.swing.JLabel powerUpFourText;
    private javax.swing.JLabel powerUpFourTitle;
    private javax.swing.JLabel powerUpOneText;
    private javax.swing.JLabel powerUpOneTitle;
    private javax.swing.JLabel powerUpThreeText;
    private javax.swing.JLabel powerUpThreeTitle;
    private javax.swing.JLabel powerUpTwoText;
    private javax.swing.JLabel powerUpTwoTitle;
    private javax.swing.JPanel powerup1Panel;
    private javax.swing.JPanel powerup2Panel;
    private javax.swing.JPanel powerup3Panel;
    private javax.swing.JPanel powerup4Panel;
    private javax.swing.JPanel powerupPanel;
    private javax.swing.JLabel textInfoLabel;
    private javax.swing.JPanel textInfoPanel;
    private App.Components.CustomLabel totalScoreLabel;
    private javax.swing.JPanel totalScorePanel;
    private javax.swing.JPanel upcomingPanel;
    // End of variables declaration//GEN-END:variables
}
