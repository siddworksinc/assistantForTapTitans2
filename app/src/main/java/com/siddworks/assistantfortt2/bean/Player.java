package com.siddworks.assistantfortt2.bean;

import com.siddworks.assistantfortt2.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Player {
	private static final String TAG = "Player";
//	public String playerID;
//	public String friendCode;
//	public String playerLevel;
//	public String lastActiveTime;
//	public String playerGold;
//	public String playerRelics;
//	public String playerDiamonds;
//	public String hasOfflineGold;
//	public String skipOfflineGold;
//	public String currentStage;
//	public String unlockedStage;
//	public String killsInWave;
//	public String beenToLatestStage;
//	public String signedIntoSocial;
//	public String shouldAskToSignInSocial;
//	public String socialID;
//	public String nextArtifactSeed;
//	public String cheater;
//	public String cheaterReason;
//	public String syncCount;
//	public String playTimeSinceLastSync;
//	public String originalTimeOffset;
//	public String totalPlaytime;
//	public String goldLostToPrestige;
//	public String dpsLostToPrestige;
//	public String lastPrestige;
//	public String uncollectedGold;
//	public String allRewardedIAPs;
//	public String cheatCount;
//	public String doubleFairyTime;
//	public String dungeonsCompleted;
//	public String dungeonStage;
//	public String gameRating;
//	public String hideTournamentPanel;
//	public String iapApprovedByAppleGoogle;
//	public String iapPurchaseCount;
//	public String iapSpendingInUSD;
//	public String isLinkedToFacebook;
//	public String language;
//	public String lastDungeonTime;
//	public String lastCompletedDungeonTime;
//	public String rewardedCustomerServiceDiamond;
//	public String rewardedCustomerServiceRelics;
//	public String backupIAPBase64EncodedTransactionReceipt;
//	public String backupIAPProductIdentifier;
//	public String calculatorMode;
//	public String currentPlayerCustomizations;
//	public String dailyClickMinValue;
//	public String facebookId;
//	public String followedTwitter;
//	public String hasOpenedFriendsPanel;
//	public String hasRatedGame;
//	public String likedFacebook;
//	public String music;
//	public String optionsAutoAdvance;
//	public String optionsFairyVideo;
//	public String optionsMultiplayerEnabled;
//	public String optionsPerformance;
//	public String playerName;
//	public String pushNotifications;
//	public String seenOfflineGoldPanel;
//	public String sound;
//	public String unlockedPlayerCustomizations;
//	public String lastUsedTexture;
//	public String canDoSeason2;
//	public String drunkenHammerRewardeReceived;
//	public String firstPurchaseMsgShown;
//	public String firstVersionInstalled;
//	public String hasCloudSaveEverSynced;
//	public String isDeveloper;
//	public String lastClickPeriod;
//	public String lastCloudSaveTime;
//	public String lastSavedVersion;
//	public String oniixTransferKey;
	private LinkedHashMap<String, HashMap<String, String>> propertiesMap = new
		LinkedHashMap<String, HashMap<String, String>>();
	public HashMap<String, String> ArtifactLevel;
	public HashMap<String, String> ArtifactRelicsSpent;
//	private HashMap<String, Artifact> artifatIdToArtifactMap;
//	private HashMap<String, Hero> heroIdToHeroMap;
//	private HashMap<String, Trophy> trophyIdToTrophyMap;
//	private Tournament tournament;
	private boolean isLoggingEnabled = true;
	private String unlockedPlayerCustomizations;
	private String artifactLevels;
	private String heroLevels;
	private String heroWeaponUpgrades;
	private int maxStageSPM;
	private String heroLevelsGirl;

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	private JSONObject jsonObject;


	//	public String getPlayerID() {
//		return playerID;
//	}
//	public void setPlayerID(String playerID) {
//		this.playerID = playerID;
//	}
//	public String getFriendCode() {
//		return friendCode;
//	}
//	public void setFriendCode(String friendCode) {
//		this.friendCode = friendCode;
//	}
//	public String getPlayerLevel() {
//		return playerLevel;
//	}
//	public void setPlayerLevel(String playerLevel) {
//		this.playerLevel = playerLevel;
//	}
//	public String getLastActiveTime() {
//		return lastActiveTime;
//	}
//	public void setLastActiveTime(String lastActiveTime) {
//		this.lastActiveTime = lastActiveTime;
//	}
//	public String getPlayerGold() {
//		return playerGold;
//	}
//	public void setPlayerGold(String playerGold) {
//		this.playerGold = playerGold;
//	}
//	public String getPlayerRelics() {
//		return playerRelics;
//	}
//	public void setPlayerRelics(String playerRelics) {
//		this.playerRelics = playerRelics;
//	}
//	@Override
//	public String toString() {
//		return "Player [playerID=" + playerID + ", friendCode=" + friendCode
//				+ ", playerLevel=" + playerLevel + ", lastActiveTime="
//				+ lastActiveTime + ", playerGold=" + playerGold
//				+ ", playerRelics=" + playerRelics + ", playerDiamonds="
//				+ playerDiamonds + ", hasOfflineGold=" + hasOfflineGold
//				+ ", skipOfflineGold=" + skipOfflineGold + ", currentStage="
//				+ currentStage + ", unlockedStage=" + unlockedStage
//				+ ", killsInWave=" + killsInWave + ", beenToLatestStage="
//				+ beenToLatestStage + "]";
//	}
	public LinkedHashMap<String, HashMap<String, String>> getPropertiesMap() {
		return propertiesMap;
	}
	public void setPropertiesMap(LinkedHashMap<String, HashMap<String, String>> propertiesMap) {
		this.propertiesMap = propertiesMap;
	}
//	public void setArtifactsMap(HashMap<String, Artifact> artifatIdToArtifactMap) {
//		this.artifatIdToArtifactMap	= artifatIdToArtifactMap;
//	}
//	public void setHeroMap(HashMap<String, Hero> heroIdToHeroMap) {
//		this.heroIdToHeroMap = heroIdToHeroMap;
//	}
//	public void setTrophyMap(HashMap<String, Trophy> trophyIdToTrophyMap) {
//		this.trophyIdToTrophyMap = trophyIdToTrophyMap;
//	}

//	public void setTournament(Tournament tournament) {
//		this.tournament = tournament;
//	}
//
//	public Tournament getTournament() {
//		return tournament;
//	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public String getWeaponSeed() {
		Log.d(TAG, " propertiesMap:" + propertiesMap);

		if (propertiesMap != null) {
			HashMap<String, String> seedsMap = propertiesMap.get("accountMap");
			Log.d(TAG, " accountMap:" + seedsMap);

			if (seedsMap != null) {
				String heroWeaponSeed = seedsMap.get("Hero Weapon Seed W1");
				return heroWeaponSeed;
			}
		}
		return null;
	}

	public String getCustomizationsString() {
		if (this.unlockedPlayerCustomizations != null) {
			String retVal = this.unlockedPlayerCustomizations.replaceAll("/", ".");
			return retVal;
		}
		return null;
	}

	public void setUnlockedPlayerCustomizations(String unlockedPlayerCustomizations) {
		this.unlockedPlayerCustomizations = unlockedPlayerCustomizations;
	}

	public void setArtifactLevels(String artifactLevels) {
		this.artifactLevels = artifactLevels;
	}

	public String getArtifactLevels() {
		return artifactLevels;
	}

	public void setHeroLevels(String heroLevels) {
		this.heroLevels = heroLevels;
	}

	public String getHeroLevels() {
		return heroLevels;
	}

	public void setHeroWeaponUpgrades(String heroWeaponUpgrades) {
		this.heroWeaponUpgrades = heroWeaponUpgrades;
	}

	public String getHeroWeaponUpgrades() {
		return heroWeaponUpgrades;
	}

	public int getMaxStageSPM() {
		return this.maxStageSPM;
	}

	public void setMaxStageSPM(int maxStageSPM) {
		this.maxStageSPM = maxStageSPM;
	}

	public String getRelics() {
		Log.d(TAG, " propertiesMap:" + propertiesMap);

		if (propertiesMap != null) {
			HashMap<String, String> worldOneMap = propertiesMap.get("worldOneMap");
			Log.d(TAG, " worldOneMap:" + worldOneMap);

			if (worldOneMap != null) {
				String totalRelics = worldOneMap.get("Total Relics");
				return totalRelics;
			}
		}
		return null;
	}

	public String getRelicsGirl() {
		Log.d(TAG, " propertiesMap:" + propertiesMap);

		if (propertiesMap != null) {
			HashMap<String, String> worldTwoMap = propertiesMap.get("worldTwoMap");
			Log.d(TAG, " worldTwoMap:" + worldTwoMap);

			if (worldTwoMap != null) {
				String totalRelics = worldTwoMap.get("Total Relics");
				return totalRelics;
			}
		}
		return null;
	}

	public String getW1ArtifactSeed() {
//		Log.d(isLoggingEnabled, TAG, " propertiesMap:" + propertiesMap);
		String seed = null;

		if (propertiesMap != null) {
			HashMap<String, String> seedMap = propertiesMap.get("accountMap");
//			Log.d(isLoggingEnabled, TAG, " accountMap:" + seedMap);

			if (seedMap != null) {
				seed = seedMap.get("Next Artifact Seed W1");
				Log.d(TAG, "seed: "+seed);
				return seed;
			}
		}
		return seed;
	}

	public String getW2ArtifactSeed() {
//		Log.d(isLoggingEnabled, TAG, " propertiesMap:" + propertiesMap);
		String seed = null;

		if (propertiesMap != null) {
			HashMap<String, String> seedMap = propertiesMap.get("accountMap");
//			Log.d(isLoggingEnabled, TAG, " accountMap:" + seedMap);

			if (seedMap != null) {
				seed = seedMap.get("Next Artifact Seed W2");
				Log.d(TAG, "seed: "+seed);
				return seed;
			}
		}
		return seed;
	}

//	public HashMap<com.siddworks.android.taptitanssavefileviewer.bean.optimizer.Artifact, Integer> getArtifactLevelsMap() {
//		HashMap<com.siddworks.android.taptitanssavefileviewer.bean.optimizer.Artifact, Integer> artLevels = new HashMap<>();
//		String artifactLevels = getArtifactLevels();
//
//		for (com.siddworks.android.taptitanssavefileviewer.bean.optimizer.Artifact artifact :
//				com.siddworks.android.taptitanssavefileviewer.bean.optimizer.Artifact.values()) {
//			artLevels.put(artifact, 0);
//		}
//
//		if (artifactLevels != null && artifactLevels.contains(".")) {
//			String[] artifactToArtifactLevels = artifactLevels.split("\\.");
//
//			for (String artifactToArtifactLevel : artifactToArtifactLevels) {
////                    Log.d(isLoggingEnabled, TAG, "artifactToArtifactLevel:"+artifactToArtifactLevel);
//
//				if(artifactToArtifactLevel != null && artifactToArtifactLevel.contains("_")) {
//					String[] artifactStrings = artifactToArtifactLevel.split("_");
//					if(artifactStrings.length > 1) {
////                            Log.d(isLoggingEnabled, TAG, "artifactStrings:"+artifactStrings);
//
//						String artifactIdString = artifactStrings[0];
//						String artifactLevelString = artifactStrings[1];
//						try {
//							int level = Integer.parseInt(artifactLevelString);
//							int artifactId = Integer.parseInt(artifactIdString);
//							artLevels.put(com.siddworks.android.taptitanssavefileviewer.bean.optimizer.Artifact.getFromId(
//									artifactId), level);
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			}
//		}
//
//		return artLevels;
//	}
//
//	public HashMap<com.siddworks.android.taptitanssavefileviewer.bean.optimizer.Hero, Integer> getHeroLevelsMap() {
//		HashMap<com.siddworks.android.taptitanssavefileviewer.bean.optimizer.Hero, Integer> heroLevelsMap = new HashMap<>();
//		String heroLevels = getHeroLevels();
//
//		for (com.siddworks.android.taptitanssavefileviewer.bean.optimizer.Hero hero : com.siddworks.android.taptitanssavefileviewer.bean.optimizer.Hero.values()) {
//			heroLevelsMap.put(hero, 0);
//		}
//
//		if(heroLevels != null && heroLevels.contains(".")) {
//			String[] levels = heroLevels.split("\\.");
//			for (String heroLevel : levels) {
//				if(heroLevel.contains("_")) {
//					String[] heroToHeroLevel = heroLevel.split("_");
//					String heroIdString = heroToHeroLevel[0];
//					String heroLevelString = heroToHeroLevel[1];
//					try {
//						int level = Integer.parseInt(heroLevelString);
//						int heroId = Integer.parseInt(heroIdString);
//						heroLevelsMap.put(com.siddworks.android.taptitanssavefileviewer.bean.optimizer.Hero.getHero(heroId), level);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//
//		return heroLevelsMap;
//	}
//
//	public HashMap<com.siddworks.android.taptitanssavefileviewer.bean.optimizer.Hero, Integer> getHeroWeaponsMap() {
//		HashMap<com.siddworks.android.taptitanssavefileviewer.bean.optimizer.Hero, Integer> heroWeaponsMap = new HashMap<>();
//		String heroWeapons = getHeroWeaponUpgrades();
//
//		for (com.siddworks.android.taptitanssavefileviewer.bean.optimizer.Hero hero
//				: com.siddworks.android.taptitanssavefileviewer.bean.optimizer.Hero.values()) {
//			heroWeaponsMap.put(hero, 0);
//		}
//
//		if(heroWeapons != null && heroWeapons.contains(".")) {
//			String[] levels = heroWeapons.split("\\.");
//			for (String heroLevel : levels) {
//				if(heroLevel.contains("_")) {
//					String[] heroToHeroLevel = heroLevel.split("_");
//					String heroIdString = heroToHeroLevel[0];
//					String heroLevelString = heroToHeroLevel[1];
//					try {
//						int level = Integer.parseInt(heroLevelString);
//						int heroId = Integer.parseInt(heroIdString);
//						heroWeaponsMap.put(com.siddworks.android.taptitanssavefileviewer.bean.optimizer.Hero.getHero(heroId), level);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//
//		return heroWeaponsMap;
//	}
//
//	public HashMap<Customisation, Boolean> getCustomizationsMap() {
//		HashMap<Customisation, Boolean> custMap = new HashMap<>();
//		String customizationsString = getCustomizationsString();
//
//		for (Customisation cust
//				: com.siddworks.android.taptitanssavefileviewer.bean.optimizer.Customisation.values()) {
//			custMap.put(cust, false);
//		}
//
//		if(customizationsString != null && customizationsString.contains(".")) {
//			String[] custs = customizationsString.split("\\.");
//			for (String cust : custs) {
//				Customisation customisation = Customisation.valueOf("C"+cust);
//				custMap.put(customisation, true);
//			}
//		}
//
//		return custMap;
//	}
//
//	public HashMap<com.siddworks.android.taptitanssavefileviewer.bean.optimizer.Hero, Integer> getHeroLevelsGirlMap() {
//		HashMap<com.siddworks.android.taptitanssavefileviewer.bean.optimizer.Hero, Integer> heroLevelsMap = new HashMap<>();
//		String heroLevels = getHeroLevelsGirl();
//
//		for (com.siddworks.android.taptitanssavefileviewer.bean.optimizer.Hero hero : com.siddworks.android.taptitanssavefileviewer.bean.optimizer.Hero.values()) {
//			heroLevelsMap.put(hero, 0);
//		}
//
//		if(heroLevels != null && heroLevels.contains(".")) {
//			String[] levels = heroLevels.split("\\.");
//			for (String heroLevel : levels) {
//				if(heroLevel.contains("_")) {
//					String[] heroToHeroLevel = heroLevel.split("_");
//					String heroIdString = heroToHeroLevel[0];
//					String heroLevelString = heroToHeroLevel[1];
//					try {
//						int level = Integer.parseInt(heroLevelString);
//						int heroId = Integer.parseInt(heroIdString);
//						heroLevelsMap.put(com.siddworks.android.taptitanssavefileviewer.bean.optimizer.Hero.getHero(heroId), level);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//
//		return heroLevelsMap;
//	}

	public String getHeroLevelsGirl() {
		return heroLevelsGirl;
	}

	public void setHeroLevelsGirl(String heroLevelsGir) {
		this.heroLevelsGirl = heroLevelsGir;
	}
}
