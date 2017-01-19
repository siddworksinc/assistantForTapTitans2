package com.siddworks.assistantfortt2.util;

import android.os.Environment;

import com.siddworks.assistantfortt2.bean.Player;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static android.content.ContentValues.TAG;

/**
 * Created by sidd on 16/1/17.
 */
public class SaveFileJsonParser {
    
    private static boolean isLoggingEnabled = true;

    public static File getDefaultSaveFile() {
        File sdcard = Environment.getExternalStorageDirectory();
        final Pattern p = Pattern.compile("ISavableGlobal.adat");
        File saveFile =  new File(sdcard.getAbsolutePath() + File.separator +
                "Android" + File.separator + "data" + File.separator +
                "com.gamehivecorp.taptitans2" + File.separator + "files" + File.separator + "ISavableGlobal.adat");

        return saveFile;
    }

    public static String readSaveFile(File saveFile) {
        Log.d(TAG, "readSaveFile() called with: saveFile = [" + saveFile + "]");

        // Get the size of the file
        long length = saveFile.length();
        Log.d(TAG, "length: "+length);

        byte[] buffer = new byte[(int) length];
        FileInputStream ios = null;
        ByteArrayOutputStream ous = null;
        int offset = 0;
        int numRead = 0;
        try {
            ios = new FileInputStream(saveFile);
            ous = new ByteArrayOutputStream();
            int read = 0;
            while (offset < buffer.length
                    && (numRead=ios.read(buffer, offset, buffer.length-offset)) >= 0) {
                offset += numRead;
            }

            // Ensure all the bytes have been read in
            if (offset < buffer.length) {
                throw new IOException("Could not completely read file "+saveFile.getName());
            }
        }
        catch (IOException e) {
            Log.e(TAG, e);
            TTHelperUtil.logError(e, "SP1", "FileReadingFailed");
            throw new RuntimeException("File reading failed");
        } finally {
            try {
                if(ous != null) {
                    ous.close();
                }
            } catch (Exception e) {
            }
            try {
                if(ios != null) {
                    ios.close();
                }
            } catch (Exception e) {
            }
        }
        String decrypted = decrypt(buffer);
        return decrypted;
    }

    private static String decrypt(byte[] bytes){
        android.util.Log.d(TAG, "decrypt() called with: bytes = [" + bytes + "]");
        byte[] iv = Arrays.copyOf(bytes, 8);

        bytes = Arrays.copyOfRange(bytes, 8, bytes.length);
        byte[] keyBytes = new byte[] { (byte)0x4b, (byte)0xc0, (byte)0x79, (byte)0x27, (byte) 0x19, (byte) 0x2f, (byte) 0x4e,
                (byte) 0x9a };

        SecretKeySpec pKey = new SecretKeySpec(keyBytes, "DES");
        IvParameterSpec iVectorSpecv = new IvParameterSpec(iv);

        try {
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS7Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, pKey, iVectorSpecv);
            return new String(cipher.doFinal(bytes), "UTF-8");
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static Player parse(String data)
    {
        init();
        Player pl = new Player();
        try {
            JSONObject playerInfoSaveStringJO = new JSONObject(data);
            fillBasicDetails(playerInfoSaveStringJO, pl);
//            fillArtifactLevelDetails(playerInfoSaveStringJO, pl);
//            fillArtifactRelicsSpentDetails(playerInfoSaveStringJO, pl);
            pl.setJsonObject(playerInfoSaveStringJO);
//				Constants.buildUi(pl.getPropertiesMap());
        } catch (JSONException e) {
            Log.e(TAG, e);
            TTHelperUtil.logError(null, "SP13", "JsonParseException");
        }
        return pl;
    }

    private static void fillArtifactRelicsSpentDetails(
            JSONObject playerInfoSaveStringJO, Player pl) throws JSONException {
        pl.ArtifactRelicsSpent = new HashMap<String, String>();
        if(!playerInfoSaveStringJO.isNull("artifactRelicsSpent")){
            JSONObject artifactRelicsSpent = playerInfoSaveStringJO.getJSONObject("artifactRelicsSpent");
            Iterator keys = artifactRelicsSpent.keys();
//			Log.d(true, "", "artifactRelicsSpent");
            while(keys.hasNext()) {
                String key = (String) keys.next();
                pl.ArtifactRelicsSpent.put(key, artifactRelicsSpent.getString(key));
//			    Log.d(true, "", "artifactIdToArtifactName.put(\""+key+"\", \""+key+"\");");
            }
//			Log.logToFile();
        }
    }

    private static void fillArtifactLevelDetails(
            JSONObject playerInfoSaveStringJO, Player pl) throws JSONException {
        pl.ArtifactLevel = new HashMap<String, String>();
        if(!playerInfoSaveStringJO.isNull("artifactLevels")){
            JSONObject artifactLevels = playerInfoSaveStringJO.getJSONObject("artifactLevels");
            Iterator keys = artifactLevels.keys();
//			Log.d(true, "", "artifactLevels");
            while(keys.hasNext()) {
                String key = (String) keys.next();
                pl.ArtifactLevel.put(key, artifactLevels.getString(key));
//			    Log.d(true, "", "artifactIdToArtifactName.put(\""+key+"\", \""+artifactLevels.getString(key)+"\");");
            }
//			Log.logToFile();
        }

    }

    private static void fillBasicDetails(JSONObject playerInfoJO,
                                         Player pl) throws JSONException {

        Iterator keys = playerInfoJO.keys();
        while(keys.hasNext()) {
            String key = (String) keys.next();
//            if(key.equals("heroSave"))
//            {
//                JSONObject heroObj = playerInfoJO.getJSONObject("heroSave");
//                if (heroObj != null && heroObj != JSONObject.NULL) {
//                    Iterator keysHero = heroObj.keys();
//                    while(keysHero.hasNext()) {
//                        String keyHero = (String) keysHero.next();
//                        populateValue(keyHero, heroObj.getString(keyHero), pl);
//                    }
//
//                    try {
//                        JSONObject heroLevelsObj = heroObj.getJSONObject("heroLevels");
//                        String heroLevels = "";
//                        if (heroLevelsObj != null && heroLevelsObj != JSONObject.NULL) {
//                            Iterator<String> heroLevelsIt = heroLevelsObj.keys();
//                            while (heroLevelsIt.hasNext()) {
//                                String hero = heroLevelsIt.next();
//                                String level = (String) heroLevelsObj.get(hero);
//                                heroLevels = heroLevels + hero + "_" + level + ".";
//                            }
//                        }
//                        if(!TextUtils.isEmpty(heroLevels) && heroLevels.length() > 0) {
//                            heroLevels = heroLevels.substring(0, heroLevels.length() - 1);
//                        }
//                        pl.setHeroLevels(heroLevels);
//                    } catch (JSONException e) {
//                        Log.e(TAG, e);
//                        TTHelperUtil.logError(null, "SP5", "HeroLevels");
//                    }
//
//                    try {
//                        JSONObject heroWeaponUpgradeObj = heroObj.getJSONObject("heroWeaponUpgrades");
//                        String heroWeaponUpgrades = "";
//                        if (heroWeaponUpgradeObj != null && heroWeaponUpgradeObj != JSONObject.NULL) {
//                            Iterator<String> heroLevelsIt = heroWeaponUpgradeObj.keys();
//                            while (heroLevelsIt.hasNext()) {
//                                String weapon = heroLevelsIt.next();
//                                String level = (String) heroWeaponUpgradeObj.get(weapon);
//                                heroWeaponUpgrades = heroWeaponUpgrades + weapon + "_" + level + ".";
//                            }
//                        }
//                        if(!TextUtils.isEmpty(heroWeaponUpgrades) && heroWeaponUpgrades.length() > 0) {
//                            heroWeaponUpgrades = heroWeaponUpgrades.substring(0, heroWeaponUpgrades.length() - 1);
//                        }
//                        pl.setHeroWeaponUpgrades(heroWeaponUpgrades);
//                    } catch (JSONException e) {
//                        Log.e(TAG, e);
//                        TTHelperUtil.logError(null, "SP6", "HeroWeaponUpgrades");
//                    }
//
//                    try {
//                        JSONObject heroLevelsObj = heroObj.getJSONObject("heroLevelsGirl");
//                        String heroLevelsGirl = "";
//                        if (heroLevelsObj != null && heroLevelsObj != JSONObject.NULL) {
//                            Iterator<String> heroLevelsIt = heroLevelsObj.keys();
//                            while (heroLevelsIt.hasNext()) {
//                                String hero = heroLevelsIt.next();
//                                String level = (String) heroLevelsObj.get(hero);
//                                heroLevelsGirl = heroLevelsGirl + hero + "_" + level + ".";
//                            }
//                        }
//                        if(!TextUtils.isEmpty(heroLevelsGirl) && heroLevelsGirl.length() > 0) {
//                            heroLevelsGirl = heroLevelsGirl.substring(0, heroLevelsGirl.length() - 1);
//                        }
//                        pl.setHeroLevelsGirl(heroLevelsGirl);
//                    } catch (JSONException e) {
//                        Log.e(TAG, e);
//                        TTHelperUtil.logError(null, "SP5", "HeroLevels");
//                    }
//                }
//            }
//            else if(key.equals("powerUpInventory"))
//            {
//                try {
//                    JSONObject puiObj = playerInfoJO.getJSONObject("powerUpInventory");
//                    if (puiObj != null && puiObj != JSONObject.NULL) {
//                        Iterator keysPUI = puiObj.keys();
//                        while(keysPUI.hasNext()) {
//                            String keyPUI = (String) keysPUI.next();
//                            populateValue(keyPUI, puiObj.getString(keyPUI), pl);
//                        }
//                    }
//                } catch (JSONException e) {
//                    Log.e(TAG, e);
//                    TTHelperUtil.logError(null, "SP7", "PowerUpInventory");
//                }
//            }
//            else if(key.equals("tournament")) {
//                try {
//                    if(playerInfoJO.has("tournament") && !playerInfoJO.isNull("tournament") &&
//                            playerInfoJO.getJSONObject("tournament") != null
//                            && playerInfoJO.getString("tournament") != JSONObject.NULL) {
//
//                        JSONObject tournamentObj = playerInfoJO.getJSONObject("tournament");
//                        if (tournamentObj != null && tournamentObj != JSONObject.NULL) {
//                            Iterator keysTournamentObj = tournamentObj.keys();
//                            while(keysTournamentObj.hasNext()) {
//                                String keyPUI = (String) keysTournamentObj.next();
//                                populateValue(keyPUI, tournamentObj.getString(keyPUI), pl);
//                            }
//                            String jsonString = tournamentObj.toString();
////                            Gson gson = new Gson();
////                            Tournament tournament = gson.fromJson(jsonString, Tournament.class);
////                            pl.setTournament(tournament);
//                        }
//                    }
//                } catch (Exception e) {
//                    Log.e(TAG, e);
//                    TTHelperUtil.logError(null, "SP8", "Tournament");
//                }
//            }
//            else if(key.equals("unlockedPlayerCustomizations")) {
//                try {
//                    String unlockedPlayerCustomizations = playerInfoJO.getString("unlockedPlayerCustomizations");
//                    if (unlockedPlayerCustomizations != null && unlockedPlayerCustomizations != JSONObject.NULL) {
//                        pl.setUnlockedPlayerCustomizations(unlockedPlayerCustomizations);
//                    }
//                } catch (JSONException e) {
//                    Log.e(TAG, e);
//                    TTHelperUtil.logError(null, "SP12", "UnlockedPlayerCustomizations");
//                }
//            }
//            else if(key.equals("trophyProgressGirl")) {
//                try {
//                    JSONObject trophyProgressGirl = playerInfoJO.getJSONObject("trophyProgressGirl");
//                    if (trophyProgressGirl != null && trophyProgressGirl != JSONObject.NULL) {
//                        JSONObject reachStage = trophyProgressGirl.getJSONObject("ReachStage");
//                        if (reachStage != null) {
//                            int progress = reachStage.getInt("progress");
//                            pl.setMaxStageSPM(progress);
//                            populateValue("maxStageGirl", String.valueOf(progress), pl);
//                        }
//                    }
//                } catch (JSONException e) {
//                    Log.e(TAG, e);
//                    TTHelperUtil.logError(null, "SP9", "TrophyProgressGirl");
//                }
//            }
//            else if(key.equals("trophyProgress")) {
//                try {
//                    JSONObject trophyProgressGirl = playerInfoJO.getJSONObject("trophyProgress");
//                    if (trophyProgressGirl != null && trophyProgressGirl != JSONObject.NULL) {
//                        JSONObject reachStage = trophyProgressGirl.getJSONObject("ReachStage");
//                        if (reachStage != null) {
//                            int progress = reachStage.getInt("progress");
//                            pl.setMaxStageSPM(progress);
//                            populateValue("maxStage", String.valueOf(progress), pl);
//                        }
//                    }
//                } catch (JSONException e) {
//                    Log.e(TAG, e);
//                    TTHelperUtil.logError(null, "SP10", "TrophyProgress");
//                }
//            }
//            else if(key.equals("artifactLevels")) {
//                try {
//                    JSONObject artifactLevelsObj = playerInfoJO.getJSONObject("artifactLevels");
////				Log.d(isLoggingEnabled, TAG, " artifactLevelsObj:" + artifactLevelsObj);
//
//                    String artifactLevels = "";
//                    if (artifactLevelsObj != null && artifactLevelsObj != JSONObject.NULL) {
//                        Iterator<String> keysArtifactLevels = artifactLevelsObj.keys();
//                        while (keysArtifactLevels.hasNext()) {
//                            String artifact = keysArtifactLevels.next();
//                            int level = (int) artifactLevelsObj.get(artifact);
//                            artifactLevels = artifactLevels + artifact.replace("Artifact", "") + "_" + level + ".";
//                        }
//                    }
//                    if(!TextUtils.isEmpty(artifactLevels) && artifactLevels.length() > 0) {
//                        artifactLevels = artifactLevels.substring(0, artifactLevels.length() - 1);
//                    }
//                    pl.setArtifactLevels(artifactLevels);
//
//                } catch (JSONException e) {
//                    Log.e(TAG, e);
//                    TTHelperUtil.logError(null, "SP11", "ArtifactLevels");
//                }
//            }
//            else {
                if(playerInfoJO.get(key) != null && playerInfoJO.get(key) != JSONObject.NULL) {
                    JSONObject parentObj = playerInfoJO.getJSONObject(key);
                    Iterator keysParent = parentObj.keys();
                    while(keysParent.hasNext()) {
                        String keyParent = (String) keysParent.next();
                        if(parentObj.get(keyParent) != null && parentObj.get(keyParent) != JSONObject.NULL) {
                            JSONObject jsonObject = parentObj.optJSONObject(keyParent);
                            if(jsonObject != null && jsonObject.has("$content")) {
                                populateValue(keyParent, jsonObject.getString("$content"), pl);
                            }
                        }
                    }
                }
            if(key != null && key.equals("TutorialModel")) {
                JSONObject tutorialModelObj = playerInfoJO.getJSONObject(key);
                Iterator tutModelObjKeys = tutorialModelObj.keys();
                while(tutModelObjKeys.hasNext()) {
                    String tutModelObjKey = (String) tutModelObjKeys.next();
                    if(tutorialModelObj.get(tutModelObjKey) != null && tutorialModelObj.get(tutModelObjKey) != JSONObject.NULL) {
                        JSONObject tutModelChildObj = tutorialModelObj.getJSONObject(tutModelObjKey);
                        Iterator tutModelChildKeys = tutModelChildObj.keys();
                        while(tutModelChildKeys.hasNext()) {
                            String tutModelChildKey = (String) tutModelChildKeys.next();
                            if(tutModelChildKey != null && tutModelChildKey.equals("status")) {
                                JSONObject childObj = tutModelChildObj.getJSONObject(tutModelChildKey);
                                if(childObj != null && childObj.has("$content")) {
                                    populateValue(tutModelObjKey, childObj.getString("$content"), pl);
                                }
                            }
                        }
                    }
                }
            }
//            }
        }

    }


    public static LinkedHashMap<String, String> artifactIdToArtifactName = new
            LinkedHashMap<>();
    public static LinkedHashMap<String, String> worldOneMap = new
            LinkedHashMap<>();
    public static LinkedHashMap<String, String> worldTwoMap = new
            LinkedHashMap<>();
    public static LinkedHashMap<String, String> accountMap = new
            LinkedHashMap<>();
    public static LinkedHashMap<String, String> perksMap = new
            LinkedHashMap<>();
    private static LinkedHashMap<String, String> customizationsMap = new
            LinkedHashMap<>();
    private static LinkedHashMap<String, String> analyticsMap = new
            LinkedHashMap<>();
    private static LinkedHashMap<String, String> playerMap = new
            LinkedHashMap<>();
    private static LinkedHashMap<String, String> tutorialMap = new
            LinkedHashMap<>();
    private static LinkedHashMap<String, String> relicsMap = new
            LinkedHashMap<>();
    private static LinkedHashMap<String, String> settingsMap = new
            LinkedHashMap<>();
    private static LinkedHashMap<String, String> artifactMap = new
            LinkedHashMap<>();
    private static LinkedHashMap<String, String> trophyMap = new
            LinkedHashMap<>();
    private static LinkedHashMap<String, String> PrestigeMap = new
            LinkedHashMap<>();
    private static LinkedHashMap<String, String> tournamentMap = new
            LinkedHashMap<>();
    public static LinkedHashMap<String, String> booleanMap = new
            LinkedHashMap<>();
    public static LinkedHashMap<String, HashMap<String, String>> propertiesMetaMap = new
            LinkedHashMap<>();
    public static LinkedHashMap<String, String> homeNameMap = new
            LinkedHashMap<>();
    public static LinkedHashMap<String, String> heroIdToHeroName;
    public static LinkedHashMap<String, String> skillIdToSkillName;
    public static LinkedHashMap<String, String> trophyIdToTrophyName;
    private static LinkedHashMap<String, String> heroMap = new
            LinkedHashMap<>();
    private static LinkedHashMap<String, String> stageMap = new
            LinkedHashMap<>();
    public static LinkedHashMap<String, String> swordMasterNameMap = new
            LinkedHashMap<>();
    public static LinkedHashMap<String, String> spellMasterNameMap = new
            LinkedHashMap<>();
    public static LinkedHashMap<String, String> homeMetaMap = new
            LinkedHashMap<>();
    public static LinkedHashMap<String, String> tournamentNameMap = new
            LinkedHashMap<>();
    private static LinkedHashMap<String, String> relicPredictionMapSwordMaster = new LinkedHashMap<>();
    private static HashMap<String, String> relicPredictionMapSpellMaster = new LinkedHashMap<>();

    public static void init()
    {

        accountMap.put("displayName", "Display Name");
        accountMap.put("countryCode", "Country Code");
        accountMap.put("accountStatus", "Account Status");
        accountMap.put("accountState", "Account State");
        accountMap.put("registeredTime", "Registered Time");
        accountMap.put("isDev", "Is Dev");
        accountMap.put("installedVersion", "Installed Version");
        accountMap.put("currentLanguage", "Current Language");
        accountMap.put("promptTimes", "Prompt Times");
        accountMap.put("promptPrestige", "Prompt Prestige");

        analyticsMap.put("canDoSwr", "canDoSwr");

        playerMap.put("gold", "Gold");
        playerMap.put("manaPotionSpent", "Mana Potion Spent");
        playerMap.put("manaPotionRecievedLocal", "Mana Potion Rcvd Local");
        playerMap.put("manaPotionRecievedServer", "Mana Potion Rcvd Server");
        playerMap.put("relicsSpentServer", "Relics Spent Server");
        playerMap.put("relicsReceivedServer", "Relics Received Server");
        playerMap.put("level", "Level");
        playerMap.put("baseDamage", "Base Damage");
        playerMap.put("currentMana", "Current Mana");
        playerMap.put("manaPotionTimer", "Mana Potion Timer");
        playerMap.put("skillPointsReceivedServer", "Skill Points Received Server");
        playerMap.put("skillPointsSpentServer", "Skill Points Spent Server");
        playerMap.put("lastSkillPointsCollectedStage", "Last Skill Points Collected Stage");
        playerMap.put("dpsLostToPrestige", "Dps Lost To Prestige");
        playerMap.put("goldLostToPrestige", "Gold Lost To Prestige");
        playerMap.put("goldSincePrestige", "Gold Since Prestige");
        playerMap.put("hasRatedGame", "Has Rated Game");
        playerMap.put("holidayBundleEmail", "Holiday Bundle Email");

        PrestigeMap.put("relicDrop", "Relic Drop");
        PrestigeMap.put("maxPrestigeStageCount", "Max Prestige Stage Count");
        PrestigeMap.put("lastPrestigeTime", "Last Prestige Time");

        stageMap.put("currentStage", "Current Stage");
        stageMap.put("autoAdvance", "Auto Advance");
        stageMap.put("enemyKillCount", "Enemy Kill Count");
        stageMap.put("bossDefeated", "Boss Defeated");

        tutorialMap.put("PlayerTab", "Player Tab");
        tutorialMap.put("HelperTab", "Helper Tab");
        tutorialMap.put("PetTab", "Pet Tab");
        tutorialMap.put("ActiveSkill", "Active Skill");
        tutorialMap.put("BossBattle", "Boss Battle");
        tutorialMap.put("SkillPoint", "Skill Point");
        tutorialMap.put("Clan", "Clan");
        tutorialMap.put("Shop", "Shop");
        tutorialMap.put("InactiveGold", "InactiveGold");
        tutorialMap.put("ArtifactTab", "Artifact Tab");
        tutorialMap.put("Transmorph", "Transmorph");

        settingsMap.put("optionVolume", "Option Volume");
        settingsMap.put("optionMusic", "Option Music");
        settingsMap.put("optionNotification", "Option Notification");
        settingsMap.put("upgradeAmount", "Upgrade Amount");
        settingsMap.put("roundedUpgrade", "Rounded Upgrade");
        settingsMap.put("showScientific", "Show Scientific");

//        settingsMap.put("MonsterKilltier", "Monster Kill Tier");
//        settingsMap.put("MonsterKillprogress", "Monster Kill Progress");
//        settingsMap.put("MonsterKillstatus", "Monster Kill Status");
//        settingsMap.put("CollectGoldtier", "Collect Gold Tier");
//        settingsMap.put("CollectGoldprogress", "Collect Gold Progress");
//        settingsMap.put("CollectGoldstatus", "Collect Gold Status");
//        settingsMap.put("UnlockHeroestier", "Unlock Heroes Tier");
//        settingsMap.put("UnlockHeroesprogress", "Unlock Heroes Progress");
//        settingsMap.put("UnlockHeroesstatus", "Unlock Heroes Status");
//
//        settingsMap.put("UnlockHeroestier", "Unlock Heroes Tier");
//        settingsMap.put("UnlockHeroesprogress", "Unlock Heroes Progress");
//        settingsMap.put("UnlockHeroesstatus", "Unlock Heroes Status");
//
//        settingsMap.put("UnlockHeroestier", "Unlock Heroes Tier");
//        settingsMap.put("UnlockHeroesprogress", "Unlock Heroes Progress");
//        settingsMap.put("UnlockHeroesstatus", "Unlock Heroes Status");

        booleanMap.put("true", "Yes");
        booleanMap.put("false", "No");

        perksMap.put("doomCreditsSpent", "Doom Credits Spent");
        perksMap.put("doomCreditsRecieved", "Doom Credits Recieved");
        perksMap.put("makeItRainCreditsSpent", "Make It Rain Credits Spent");
        perksMap.put("makeItRainCreditsRecieved", "Make It Rain Credits Recieved");
        perksMap.put("clanMakeItRainCreditsSpent", "Clan Make It Rain Credits Spent");
        perksMap.put("clanMakeItRainCreditsRecieved", "Clan Make It Rain Credits Recieved");
        perksMap.put("powerOfSwipeCreditsSpent", "Power Of Swipe Credits Spent");
        perksMap.put("powerOfSwipeCreditsRecieved", "Power Of Swipe Credits Recieved");
        perksMap.put("doubleDamageCreditsSpent", "Double Damage Credits Spent");
        perksMap.put("doubleDamageCreditsRecieved", "Double Damage Credits Recieved");


//		perksMap.put("playerID", "Player ID");
//        perksMap.put("world", "Current World");
//        perksMap.put("skipOfflineGold", "Skip Offline Gold");
//        perksMap.put("lastClickPeriod", "Last Click Period");
//        perksMap.put("totalPlaytime", "Total Playtime");
//        perksMap.put("originalTimeOffset", "Original Time Offset");
//        perksMap.put("goldLostToPrestige", "Gold Lost To Prestige");
//        perksMap.put("dpsLostToPrestige", "Dps Lost To Prestige");
//        perksMap.put("hasOfflineGold", "Has Offline Gold");
//        perksMap.put("lastSavedVersion", "Last Saved Version");
//        perksMap.put("doubleFairyTime", "Double Fairy Time");
//        perksMap.put("dungeonStage", "Dungeon Stage");
//        perksMap.put("lastCompletedDungeonTime", "Last Completed Dungeon Time");
//        perksMap.put("lastDungeonTime", "Last Dungeon Time");
//        perksMap.put("playerDiamonds", "Player Diamonds");
//        perksMap.put("firstVersionInstalled", "First Version Installed");
//        perksMap.put("optionsAutoAdvance", "Options Auto Advance");
//        perksMap.put("gameRating", "Game Rating");
//        perksMap.put("dungeonsCompleted", "Dungeons Completed");
//        perksMap.put("canDoSeason2", "Can Do Season2");
//        perksMap.put("lastCloudSaveTime", "Last Cloud Save Time");
//        perksMap.put("pushNotifications", "Push Notifications");
//        perksMap.put("cleanMasterDiamonds", "Clean Master Diamonds");
//        perksMap.put("artifactRelicsSpentFixed", "Artifacts Relics Spent Fixed");
//        perksMap.put("superPrestige", "Super Prestige");

        propertiesMetaMap.put("worldOneMap", worldOneMap);
        propertiesMetaMap.put("worldTwoMap", worldTwoMap);
        propertiesMetaMap.put("accountMap", accountMap);
        propertiesMetaMap.put("analyticsMap", analyticsMap);
        propertiesMetaMap.put("playerMap", playerMap);
        propertiesMetaMap.put("perksMap", perksMap);
        propertiesMetaMap.put("customizationsMap", customizationsMap);
        propertiesMetaMap.put("tutorialMap", tutorialMap);
        propertiesMetaMap.put("relicsMap", relicsMap);
//		propertiesMetaMap.put("artifactMap", artifactMap);
        propertiesMetaMap.put("trophyMap", trophyMap);
        propertiesMetaMap.put("PrestigeMap", PrestigeMap);
        propertiesMetaMap.put("settingsMap", settingsMap);
        propertiesMetaMap.put("heroMap", heroMap);
        propertiesMetaMap.put("stageMap", stageMap);
        propertiesMetaMap.put("tournamentMap", tournamentMap);
        propertiesMetaMap.put("relicPredictionMapSwordMaster", relicPredictionMapSwordMaster);
        propertiesMetaMap.put("relicPredictionMapSpellMaster", relicPredictionMapSpellMaster);

        homeNameMap.put("worldOneMap", "SwordMaster");
        homeNameMap.put("worldTwoMap", "SpellMaster");
        homeNameMap.put("accountMap", "Account");
        homeNameMap.put("perksMap", "Perks");
        homeNameMap.put("customizationsMap", "Customizations");
        homeNameMap.put("analyticsMap", "Analytics");
        homeNameMap.put("playerMap", "Player");
        homeNameMap.put("tutorialMap", "Tutorial");
        homeNameMap.put("relicsMap", "Relics");
        homeNameMap.put("artifactMap", "Artifacts");
        homeNameMap.put("trophyMap", "Trophies");
        homeNameMap.put("PrestigeMap", "Prestige");
        homeNameMap.put("tournamentMap", "Tournaments");
        homeNameMap.put("settingsMap", "Settings");
        homeNameMap.put("heroMap", "Heroes");
        homeNameMap.put("stageMap", "Stage");
        homeNameMap.put("relicPredictionMapSwordMaster", "Relic Increase");
        homeNameMap.put("relicPredictionMapSpellMaster", "Relic Increase");

        swordMasterNameMap.put("worldOneMap", "World One");

        spellMasterNameMap.put("worldTwoMap", "World Two");

        tournamentNameMap.put("tournamentMap", "Tournament");

        relicPredictionMapSwordMaster.put("currentStage", "Current Stage");
        relicPredictionMapSwordMaster.put("relicsCurrentStage", "Relics at Current Stage");
        relicPredictionMapSwordMaster.put("nextRelicStage", "Next Relic Increase Stage");
        relicPredictionMapSwordMaster.put("relicsNextStage", "Relics at Next Breakpoint");

        relicPredictionMapSpellMaster.put("currentStageGirl", "Current Stage");
        relicPredictionMapSpellMaster.put("relicsCurrentStage", "Relics at Current Stage");
        relicPredictionMapSpellMaster.put("nextRelicStage", "Next Relic Increase Stage");
        relicPredictionMapSpellMaster.put("relicsNextStage", "Relics at Next Breakpoint");

        tournamentMap.put("groupID", "Group ID");
        tournamentMap.put("tournamentPoints", "Tournaments Points");
        tournamentMap.put("stageProgress", "Stage Progress");
        tournamentMap.put("stageReported", "Stage Reported");
        tournamentMap.put("startTime", "Start Time");
        tournamentMap.put("endTime", "End Time");
        tournamentMap.put("prizeClaimed", "Prize Claimed");
        tournamentMap.put("retired", "Retired");
        tournamentMap.put("shownTournamentRenamePrompt", "Shown Tournament Rename Prompt");
        tournamentMap.put("tournamentType", "Tournament Type");

        homeMetaMap.put("accountMap", "Seeds");
        homeMetaMap.put("PrestigeMap", "Cheater");
        homeMetaMap.put("perksMap", "Basic Details");
        homeMetaMap.put("playerMap", "Miscellaneous");
        homeMetaMap.put("customizationsMap", "Customizations");
        homeMetaMap.put("relicsMap", "Relics");
        homeMetaMap.put("artifactMap", "Artifacts");
        homeMetaMap.put("trophyMap", "Trophies");
        homeMetaMap.put("tournamentMap", "Tournaments");
        homeMetaMap.put("settingsMap", "Game Settings");
        homeMetaMap.put("heroMap", "Heroes");
        homeMetaMap.put("stageMap", "Perks");
        homeMetaMap.put("tutorialMap", "Social");
        homeMetaMap.put("analyticsMap", "In App Purchases");

        worldOneMap.put("playerName", "Player Name");
        worldOneMap.put("maxStage", "Max Stage");
        worldOneMap.put("playerLevel", "Sword Master Level");
        worldOneMap.put("playerGold", "Player Gold");
        worldOneMap.put("playerRelics", "Total Relics");
        worldOneMap.put("unclaimedWeaponUpgrades", "Unclaimed Weapon Upgrades");
        worldOneMap.put("currentStage", "Current Stage");
        worldOneMap.put("unlockedStage", "Unlocked Stage");
        worldOneMap.put("killsInWave", "Kills In Wave");
        worldOneMap.put("beenToLatestStage", "Been To Latest Stage");
        worldOneMap.put("superWeapon", "Super Weapon");
        worldOneMap.put("lastPrestige", "Last Prestige");
        worldOneMap.put("uncollectedGold", "Uncollected Gold");
        worldOneMap.put("lastActiveTime", "Last Active Time");

        worldTwoMap.put("playerNameGirl", "Player Name");
        worldTwoMap.put("maxStageGirl", "Max Stage");
        worldTwoMap.put("playerLevelGirl", "Spell Master Level");
        worldTwoMap.put("playerGoldGirl", "Player Gold");
        worldTwoMap.put("playerRelicsGirl", "Total Relics");
        worldTwoMap.put("unclaimedWeaponUpgradesGirl", "Unclaimed Weapon Upgrades");
        worldTwoMap.put("currentStageGirl", "Current Stage");
        worldTwoMap.put("unlockedStageGirl", "Unlocked Stage");
        worldTwoMap.put("killsInWaveGirl", "Kills In Wave");
        worldTwoMap.put("beenToLatestStageGirl", "Been To Latest Stage");
        worldTwoMap.put("superWeaponGirl", "Super Weapon");
        worldTwoMap.put("lastPrestigeGirl", "Last Prestige");
        worldTwoMap.put("uncollectedGoldGirl", "Uncollected Gold");
        worldTwoMap.put("lastActiveTimeGirl", "Last Active Time");

    }

    public static void populateValue(String key, String value, Player pl)
    {
        value = booleanMap.get(value) == null ? value : booleanMap.get(value);
        if(value == null) {
            return;
        }

        // iterate on main map
        Iterator pmIt = propertiesMetaMap.entrySet().iterator();
        while(pmIt.hasNext()) {
            String pmMapKey = (String) ((Map.Entry)pmIt.next()).getKey();
            HashMap<String, String> pmMapValue = propertiesMetaMap.get(pmMapKey);

            String keyMap = pmMapValue.get(key);
            if(keyMap != null)
            {
                if(pl.getPropertiesMap().get(pmMapKey) != null)
                {
                    pl.getPropertiesMap().get(pmMapKey).put(keyMap, value);
                }
                else
                {
                    pl.getPropertiesMap().put(pmMapKey, new HashMap<String, String>());
                    pl.getPropertiesMap().get(pmMapKey).put(keyMap, value);
                }
                break;
            }
        }
    }


}
