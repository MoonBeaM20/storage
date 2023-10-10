package com.wk.proviso.testscripts;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.wk.proviso.common.UtilLib;
import com.wk.proviso.pages.GlobalSearchPage;
import com.wk.proviso.pages.LoginPage;
import com.wk.proviso.pages.MaterialityPage;
import com.wk.provisoframework.controlsLibrary.ProvisoControls.ProvisoControls;
import com.wk.provisoframework.reporting.Reporting;

public class MaterialityPageTest extends ProvisoControls {
	
	private String internalUser = "testAdminUser@wolterskluwer.com";
	private String externalUser = "admin.expert@abcbank.com";
	private String password = "Test@123";
	
	private LoginPage loginPage = null;
	private GlobalSearchPage globalSearchPage = null;
	private MaterialityPage materialityPage = null;
	
	@BeforeMethod
	public void setup() {
		_webcontrols.get().resetImplicitWait("Set implicit wait", 10);
		loginPage = new LoginPage(_webcontrols);
		globalSearchPage = new GlobalSearchPage(_webcontrols);
		materialityPage = new MaterialityPage(_webcontrols);
	}
	
	@Test
	public void testCase_tc_01() throws AWTException {
		String testDescription = "Verify User able to get Material alert in CMD when Substantive as Material and Non-Substantive as Material under Change Impact and compare with Cluster Attributes";
		String primaryTopic = "Limitations on Fees and Charges (Misc.)";
		String clusterName = "testm";
		String materiality = "Material";
		performTestCases1to3("TC_01", testDescription, 1, primaryTopic, clusterName, 1, materiality);
	}
	
	@Test
	public void testCase_tc_02() throws AWTException {
		String testDescription = "Verify User able to get Material alert in CMD when Substantive as Material and Non-Substantive as Immaterial under Change Impact and compare with Cluster Attributes";
		String primaryTopic = "Limitations on Fees and Charges (Misc.)";
		String clusterName = "testm";
		String materiality = "Material";
		performTestCases1to3("TC_02", testDescription, 2, primaryTopic, clusterName, 2, materiality);
	}
	
	@Test
	public void testCase_tc_03() throws AWTException {
		String testDescription = "Verify User able to get Material alert in CMD when Substantive as Immaterial and Non-Substantive as Material under Change Impact and compare with Cluster Attributes";
		String primaryTopic = "Limitations on Fees and Charges (Misc.)";
		String clusterName = "testm";
		String materiality = "Material";
		performTestCases1to3("TC_03", testDescription, 3, primaryTopic, clusterName, 3, materiality);
	}
	
	private void  performTestCases1to3(String testCaseId, String testDescription, int materialitySettingsRowNum, String primaryTopic, String clusterName, int clusterMaterialityRowNum, String materiality) throws AWTException {
		Reporting.getLogger().log(LogStatus.INFO, "<b>Test Case ID: "+testCaseId+"</b>", "<b>"+testDescription+"</b>");
		
		// login
		Assert.assertTrue(loginPage.loginToProViso(externalUser, password), "Unable to login");
		
		// set materiality
		setMaterialitySettings(materialitySettingsRowNum);
		
		// logout and login
		logoutAndLogin(internalUser, password);
		
		// search by primary topic
		searchByPrimaryTopic(primaryTopic);
		
		// get cluster page
		Assert.assertTrue(materialityPage.getClusterPage(), "Unable to get cluster page");
		
		// click edit cluster icon
		Assert.assertTrue(materialityPage.clickEditCluster(clusterName), "Unable to click edit cluster icon");
		
		// updating cluster fields
		int randomNumber = ThreadLocalRandom.current().nextInt(100);
		
		Assert.assertTrue(materialityPage.enterClusterReleaseComments("release comments " + randomNumber), "Unable to enter release comments");
		Assert.assertTrue(materialityPage.enterClusterGroupingExplanation("grouping explanation " + randomNumber), "Unable to enter grouping explanation");
		Assert.assertTrue(materialityPage.enterClusterFederalLawCitationCompared("federal law citation compared " + randomNumber), "Unable to enter federal law citation compared");
		Assert.assertTrue(materialityPage.enterClusterComparativeFederalStateAnalysis("comparative federal state analysis " + randomNumber), "Unable to enter comparative federal state analysis");
		Assert.assertTrue(materialityPage.clickClusterSaveButton(), "Unable to click save button");
		
		// set cluster materiality
		setClusterMateriality(clusterMaterialityRowNum);
		
		// logout and login
		logoutAndLogin(externalUser, password);
		
		// verify materiality on CMD notifications page
		Assert.assertTrue(materialityPage.viewCMDNotifications(), "Unable to navigate to CMD notifications page");
		Assert.assertTrue(materialityPage.verifyClusterChangeMateriality(clusterName, materiality), "Invalid materiality");
		
		Reporting.getLogger().log(LogStatus.INFO, "<b>Test Case ID: "+testCaseId+"</b>","<b>End test case</b>");	
	}
	
	@Test
	public void testCase_tc_04() throws AWTException {
		String testDescription = "Verify User able to get Material alert in CMD when Substantive as Material and Non-Substantive as Material under Change Impact and compare with Requirement Applicability";
		String primaryTopic = "Lending and Financing";
		String requirementName = "New req - ni3 15";
		String materiality = "Material";
		performTestCases4to6("TC_04", testDescription, 4, primaryTopic, requirementName, "Yes", 4, materiality);
	}
	
	@Test
	public void testCase_tc_05() throws AWTException {
		String testDescription = "Verify User able to get Material alert in CMD when Substantive as Material and Non-Substantive as Immaterial under Change Impact and compare with Requirement Applicability";
		String primaryTopic = "Lending and Financing";
		String requirementName = "New req - ni3 15";
		String materiality = "Material";
		performTestCases4to6("TC_05", testDescription, 5, primaryTopic, requirementName, "No", 5, materiality);
	}
	
	@Test
	public void testCase_tc_06() throws AWTException {
		String testDescription = "Verify User able to get Material alert in CMD when Substantive as Immaterial and Non-Substantive as Material under Change Impact and compare with Requirement Applicability";
		String primaryTopic = "Lending and Financing";
		String requirementName = "New req - ni3 15";
		String materiality = "Material";
		performTestCases4to6("TC_06", testDescription, 6, primaryTopic, requirementName, "Yes", 6, materiality);
	}
	
	private void performTestCases4to6(String testCaseId, String testDescription, int materialitySettingsRowNum, String primaryTopic, 
			String requirementName, String requirementApplicability, int requirementMaterialityRowNum, String materiality) throws AWTException {
		Reporting.getLogger().log(LogStatus.INFO, "<b>Test Case ID: "+testCaseId+"</b>", "<b>"+testDescription+"</b>");
		
		// login
		Assert.assertTrue(loginPage.loginToProViso(externalUser, password), "Unable to login");
		
		// set materiality
		setMaterialitySettings(materialitySettingsRowNum);
		
		// wait for message to disappear
		Assert.assertTrue(materialityPage.isMaterialityPopupMessageInvisible(), "Materiality message still visible");
		
		// search by primary topic
		searchByPrimaryTopic(primaryTopic);
		
		// get requirement page
		Assert.assertTrue(materialityPage.getRequirementPage(), "Unable to get requirement page");
		
		// change requirement applicability
		Assert.assertTrue(materialityPage.changeApplicability(requirementName, requirementApplicability, Arrays.asList("Product Not Offered")));
		
		// logout and login
		logoutAndLogin(internalUser, password);
		
		// search by primary topic
		searchByPrimaryTopic(primaryTopic);
		
		// get requirement page
		Assert.assertTrue(materialityPage.getRequirementPage(), "Unable to get requirement page");
		
		// select requirement to edit
		Assert.assertTrue(materialityPage.selectRequirementToEdit(requirementName), "Unable to select requirment to edit");
		
		// updating requirement fields
		int randomNumber = ThreadLocalRandom.current().nextInt(100);
		System.out.println(randomNumber);
		
		Assert.assertTrue(materialityPage.enterRequirementSummary("new legal information " + randomNumber), "Unable to enter requirement summary");
		Assert.assertTrue(materialityPage.selectParty("Individual"), "Unable to set party");
		Assert.assertTrue(materialityPage.enterRequirementNotes("Requirment notes " + randomNumber), "Unable to enter requirement notes");
		Assert.assertTrue(materialityPage.enterReleaseNotes("New release notes " + randomNumber), "Unable to enter release notes");
		Assert.assertTrue(materialityPage.selectChangeType("WK Change", "DATA1"), "Unable to select change type");
		Assert.assertTrue(materialityPage.selectChangeType("Reg Change", "BLI"), "Unable to select change type");
		
		// save changes
		Assert.assertTrue(materialityPage.clickRequirementSaveButton());
		
		// set requirement materiality
		setRequirementMateriality(requirementMaterialityRowNum);
		
		// click associated cluster
		String associatedClusterName = materialityPage.getAssociatedClusterName(requirementName);
		Assert.assertTrue(materialityPage.clickAssociatedCluster(requirementName), "Unable to click associated cluster");
		Assert.assertTrue(materialityPage.clickEditCluster(associatedClusterName), "Unable to click edit cluster");
		
		// set cluster review status
		Assert.assertTrue(materialityPage.setClusterReviewStatus("Approved"), "Unable to set review status");
		
		// save cluster
		Assert.assertTrue(materialityPage.clickClusterSaveButton(), "Unable to click save button");
		Assert.assertTrue(materialityPage.saveChanges(Collections.emptyMap(), "Do not"), "Unable to save changes");
		Assert.assertTrue(materialityPage.submitSaveChanges(), "Unable to submit cluster changes");
		Assert.assertTrue(materialityPage.isClusterSaved(), "Cluster updates not saved");
		
		// external login
		logoutAndLogin(externalUser, password);
		
		// verify materiality on CMD notifications page
		Assert.assertTrue(materialityPage.viewCMDNotifications(), "Unable to navigate to CMD notifications page");
		CMDRefershResults();
		CMDRefershResults();
		Assert.assertTrue(materialityPage.verifyRequirementChangeMateriality(requirementName, "Material"), "Invalid materiality");
		
		Reporting.getLogger().log(LogStatus.INFO, "<b>Test Case ID: "+testCaseId+"</b>","<b>End test case</b>");	
	}
	
	@Test
	public void testCase_tc_07() throws AWTException {
		String testDescription = "Verify User able to get Material alert in CMD when Substantive as Material and Non-Substantive as Material under Change Impact and compare with Requirement Attributes";
		String primaryTopic = "Lending and Financing";
		String requirementName = "New req - ni3 15";
		String materiality = "Material";
		performTestCases7to9("TC_07", testDescription, 7, primaryTopic, requirementName, 7, materiality);
	}
	
	@Test
	public void testCase_tc_08() throws AWTException {
		String testDescription = "Verify User able to get Material alert in CMD when Substantive as Material and Non-Substantive as Immaterial under Change Impact and compare with Requirement Attributes";
		String primaryTopic = "Lending and Financing";
		String requirementName = "New req - ni3 15";
		String materiality = "Material";
		performTestCases7to9("TC_08", testDescription, 8, primaryTopic, requirementName, 8, materiality);
	}
	
	@Test
	public void testCase_tc_09() throws AWTException {
		String testDescription = "Verify User able to get Material alert in CMD when Substantive as Immaterial and Non-Substantive as Material under Change Impact and compare with Requirement Attributes";
		String primaryTopic = "Lending and Financing";
		String requirementName = "New req - ni3 15";
		String materiality = "Material";
		performTestCases7to9("TC_09", testDescription, 9, primaryTopic, requirementName, 9, materiality);
	}
	
	private void performTestCases7to9(String testCaseId, String testDescription, int materialitySettingsRowNum, String primaryTopic, 
			String requirementName, int requirementMaterialityRowNum, String materiality) throws AWTException {
		Reporting.getLogger().log(LogStatus.INFO, "<b>Test Case ID: "+testCaseId+"</b>", "<b>"+testDescription+"</b>");
		
		// login
		Assert.assertTrue(loginPage.loginToProViso(externalUser, password), "Unable to login");
		
		// set materiality
		setMaterialitySettings(materialitySettingsRowNum);
		
		// logout and login
		logoutAndLogin(internalUser, password);
		
		// search by primary topic
		searchByPrimaryTopic(primaryTopic);
		
		// get requirement page
		Assert.assertTrue(materialityPage.getRequirementPage(), "Unable to get requirement page");
		
		// select requirement to edit
		Assert.assertTrue(materialityPage.selectRequirementToEdit(requirementName), "Unable to select requirment to edit");
		
		// updating requirement fields
		int randomNumber = ThreadLocalRandom.current().nextInt(100);
		System.out.println(randomNumber);
		
		Assert.assertTrue(materialityPage.enterRequirementSummary("new legal information " + randomNumber), "Unable to enter requirement summary");
		Assert.assertTrue(materialityPage.selectParty("Individual"), "Unable to set party");
		Assert.assertTrue(materialityPage.enterRequirementNotes("Requirment notes " + randomNumber), "Unable to enter requirement notes");
		Assert.assertTrue(materialityPage.enterReleaseNotes("New release notes " + randomNumber), "Unable to enter release notes");
		Assert.assertTrue(materialityPage.selectChangeType("WK Change", "DATA1"), "Unable to select change type");
		Assert.assertTrue(materialityPage.selectChangeType("Reg Change", "BLI"), "Unable to select change type");
		
		// save changes
		Assert.assertTrue(materialityPage.clickRequirementSaveButton());
		
		// set requirement materiality
		setRequirementMateriality(requirementMaterialityRowNum);
		
		// click associated cluster
		String associatedClusterName = materialityPage.getAssociatedClusterName(requirementName);
		Assert.assertTrue(materialityPage.clickAssociatedCluster(requirementName), "Unable to click associated cluster");
		Assert.assertTrue(materialityPage.clickEditCluster(associatedClusterName), "Unable to click edit cluster");
		
		// set cluster review status
		Assert.assertTrue(materialityPage.setClusterReviewStatus("Approved"), "Unable to set review status");
		
		// save cluster
		Assert.assertTrue(materialityPage.clickClusterSaveButton(), "Unable to click save button");
		Assert.assertTrue(materialityPage.saveChanges(Collections.emptyMap(), "Do not"), "Unable to save changes");
		Assert.assertTrue(materialityPage.submitSaveChanges(), "Unable to submit cluster changes");
		Assert.assertTrue(materialityPage.isClusterSaved(), "Cluster updates not saved");
		
		// external login
		logoutAndLogin(externalUser, password);
		
		// verify materiality on CMD notifications page
		Assert.assertTrue(materialityPage.viewCMDNotifications(), "Unable to navigate to CMD notifications page");
		CMDRefershResults();
		CMDRefershResults();
		Assert.assertTrue(materialityPage.verifyRequirementChangeMateriality(requirementName, "Material"), "Invalid materiality");
		
		Reporting.getLogger().log(LogStatus.INFO, "<b>Test Case ID: "+testCaseId+"</b>","<b>End test case</b>");	
	}
	
	@Test
	public void testCase_tc_10() throws AWTException {
		String testDescription = "Verify User able to get Material alert in CMD for New-Adopted (Mirror Cluster)";
		String primaryTopic = "Do-Not-Call policies";
		String requirementToMove = "Compliance with National Do Not Call Registry";
		String clusterName = "1930 1";
		String requirementMovementType = "Requirement Added";
		String materiality = "Material";
		performTestCase10to11("TC_10", testDescription, 10, primaryTopic, requirementToMove, clusterName, 10, requirementMovementType, materiality);
	}
	
	@Test
	public void testCase_tc_11() throws AWTException {
		String testDescription = "Verify User able to get Material alert in CMD for New - Unassigned (In a WK cluster that was made into custom clusters or in an unadopted WK cluster)";
		String primaryTopic = "Do-Not-Call policies";
		String requirementToMove = "Unsolicited Fax Advertisements and Honoring Opt-Out Requests";
		String clusterName = "A R 1";
		String requirementMovementType = "Requirement Added";
		String materiality = "Material";
		performTestCase10to11("TC_11", testDescription, 11, primaryTopic, requirementToMove, clusterName, 11, requirementMovementType, materiality);
	}
	
	public void performTestCase10to11(String testCaseId, String testDescription, int materialitySettingsRowNum, String primaryTopic, 
			String requirementToMove, String clusterName, int requirementMaterialityRowNum, String requirementMovementType, String materiality) throws AWTException {
		Reporting.getLogger().log(LogStatus.INFO, "<b>Test Case ID: "+testCaseId+"</b>", "<b>"+testDescription+"</b>");
		
		// login
		Assert.assertTrue(loginPage.loginToProViso(externalUser, password), "Unable to login");
		
		// set materiality
		setMaterialitySettings(materialitySettingsRowNum);
		
		// logout and login
		logoutAndLogin(internalUser, password);
		
		// search by primary topic
		searchByPrimaryTopic(primaryTopic);
		
		// get requirement page
		Assert.assertTrue(materialityPage.getClusterPage(), "Unable to get cluster page");
		
		// select requirement to edit
		Assert.assertTrue(materialityPage.viewClusterRequirements("Unclustered Requirement"), "Unable to click cluster expand icon");
		
		// move requirement
		Assert.assertTrue(materialityPage.moveRequirement(requirementToMove, clusterName), "Unable to move requirement");
		
		// save changes
		Assert.assertTrue(materialityPage.saveChanges(Collections.emptyMap(), "Do not"), "Unable to save changes");
		
		// submit changes
		Assert.assertTrue(materialityPage.submitSaveChanges(), "Unable to submit changes");
		
		// verify changes
		Assert.assertTrue(materialityPage.isRequirementMoved(), "Requirement not moved");
		
		// click edit cluster
		Assert.assertTrue(materialityPage.clickEditCluster(clusterName), "Unable to click edit cluster");
		
		// set cluster review status
		Assert.assertTrue(materialityPage.setClusterReviewStatus("Approved"), "Unable to set review status");
		
		// save cluster
		Assert.assertTrue(materialityPage.clickClusterSaveButton(), "Unable to click save button");
		Assert.assertTrue(materialityPage.saveChanges(Collections.emptyMap(), "Do not"), "Unable to save changes");
		Assert.assertTrue(materialityPage.submitSaveChanges(), "Unable to submit cluster changes");
		Assert.assertTrue(materialityPage.isClusterSaved(), "Cluster updates not saved");
		
		// external login
		logoutAndLogin(externalUser, password);
		
		// verify materiality on CMD notifications page
		Assert.assertTrue(materialityPage.viewCMDNotifications(), "Unable to navigate to CMD notifications page");
		CMDRefershResults();
		CMDRefershResults();
		Assert.assertTrue(materialityPage.verifyRequirementMovementMateriality(clusterName, requirementMovementType, "Material"), "Invalid materiality");
		
		Reporting.getLogger().log(LogStatus.INFO, "<b>Test Case ID: "+testCaseId+"</b>","<b>End test case</b>");
		
		undoRequirementMove(primaryTopic, requirementToMove, clusterName);
	}
	
	private void undoRequirementMove(String primaryTopic, String requirementName, String clusterName) throws AWTException {
		logoutAndLogin(internalUser, password);
		searchByPrimaryTopic(primaryTopic);
		Assert.assertTrue(materialityPage.getClusterPage(), "Unable to get cluster page");
		Assert.assertTrue(materialityPage.viewClusterRequirements(clusterName), "Unable to click expand icon");
		Assert.assertTrue(materialityPage.moveRequirement(requirementName, "Unclustered Requirements"), "Unabel to move requirement");
		Assert.assertTrue(materialityPage.saveChanges(Collections.emptyMap(), "Do not"), "Unable to save changes");
		Assert.assertTrue(materialityPage.submitSaveChanges(), "Unable to submit changes");
		Assert.assertTrue(materialityPage.isRequirementMoved(), "Requirement not moved");
		Assert.assertTrue(materialityPage.clickEditCluster(clusterName), "Unable to click edit cluster");
		Assert.assertTrue(materialityPage.setClusterReviewStatus("Approved"), "Unable to set review status");
		Assert.assertTrue(materialityPage.clickClusterSaveButton(), "Unable to click save button");
		Assert.assertTrue(materialityPage.saveChanges(Collections.emptyMap(), "Do not"), "Unable to save changes");
		Assert.assertTrue(materialityPage.submitSaveChanges(), "Unable to submit cluster changes");
		Assert.assertTrue(materialityPage.isClusterSaved(), "Cluster updates not saved");
	}
	
	@Test
	public void testCase_tc_12() throws AWTException {
		String testDescription = "Verify User able to get Material alert in CMD for Existing - Adopted (In a mirror or custom cluster)";
		String primaryTopic = "Do-Not-Call policies";
		String requirementName = "Do not call - js 2";
		String clusterName = "";
		String materiality = "Material";
		performTestCases12to13("TC_12", testDescription, 7, primaryTopic, requirementName, clusterName, 7, materiality);
	}
	
	private void performTestCases12to13(String testCaseId, String testDescription, int materialitySettingsRowNum, String primaryTopic, 
			String requirementName, String clusterName, int requirementMaterialityRowNum, String materiality) throws AWTException {
		Reporting.getLogger().log(LogStatus.INFO, "<b>Test Case ID: "+testCaseId+"</b>", "<b>"+testDescription+"</b>");
		
		// login
		Assert.assertTrue(loginPage.loginToProViso(externalUser, password), "Unable to login");
		
		// set materiality
		setMaterialitySettings(materialitySettingsRowNum);
		
		// logout and login
		logoutAndLogin(internalUser, password);
		
		// search by primary topic
		searchByPrimaryTopic(primaryTopic);
		
		// get requirement page
		Assert.assertTrue(materialityPage.getRequirementPage(), "Unable to get requirement page");
		
		// select requirement to edit
		Assert.assertTrue(materialityPage.selectRequirementToEdit(requirementName), "Unable to select requirment to edit");
		
		// updating requirement fields
		int randomNumber = ThreadLocalRandom.current().nextInt(100);
		System.out.println(randomNumber);
		
		Assert.assertTrue(materialityPage.enterRequirementSummary("new legal information " + randomNumber), "Unable to enter requirement summary");
		Assert.assertTrue(materialityPage.selectParty("Individual"), "Unable to set party");
		Assert.assertTrue(materialityPage.enterRequirementNotes("Requirment notes " + randomNumber), "Unable to enter requirement notes");
		Assert.assertTrue(materialityPage.enterReleaseNotes("New release notes " + randomNumber), "Unable to enter release notes");
		Assert.assertTrue(materialityPage.selectChangeType("WK Change", "DATA1"), "Unable to select change type");
		Assert.assertTrue(materialityPage.selectChangeType("Reg Change", "BLI"), "Unable to select change type");
		
		// save changes
		Assert.assertTrue(materialityPage.clickRequirementSaveButton(), "Unable to click save button");
		
		// set requirement materiality
		setRequirementMateriality(requirementMaterialityRowNum);
		
		// get cluster page
		Assert.assertTrue(materialityPage.getClusterPage(), "Unable to get cluster page");
		
		// click edit cluster
		Assert.assertTrue(materialityPage.clickEditCluster(clusterName), "Unable to click edit cluster");
		
		// save cluster
		Assert.assertTrue(materialityPage.clickClusterSaveButton(), "Unable to click save button");
		Assert.assertTrue(materialityPage.saveChanges(Collections.emptyMap(), "Do not"), "Unable to save changes");
		Assert.assertTrue(materialityPage.submitSaveChanges(), "Unable to submit cluster changes");
		Assert.assertTrue(materialityPage.isClusterSaved(), "Cluster updates not saved");
		
		// external login
		logoutAndLogin(externalUser, password);
		
		// verify materiality on CMD notifications page
		Assert.assertTrue(materialityPage.viewCMDNotifications(), "Unable to navigate to CMD notifications page");
		CMDRefershResults();
		CMDRefershResults();
		Assert.assertTrue(materialityPage.verifyRequirementChangeMateriality(requirementName, "Material"), "Invalid materiality");
				
		Reporting.getLogger().log(LogStatus.INFO, "<b>Test Case ID: "+testCaseId+"</b>","<b>End test case</b>");
	}
	
	// helper methods
	
	private void logoutAndLogin(String username, String password) throws AWTException {
		// logout
		Assert.assertTrue(loginPage.logOutFromProViso(), "Unable to logout");
		
		// login
		Assert.assertTrue(loginPage.loginToProViso(username, password), "Unable to login");
	}
	
	public void searchByPrimaryTopic(String primaryTopic) {
		// click search drop down
		Assert.assertTrue(globalSearchPage.clickSearchDropDown(), "Unable to click search drop down");
		
		// select primary topic
		Assert.assertTrue(globalSearchPage.selectPrimaryTopic(primaryTopic), "Unable to select primary topic");
		
		// click search button
		Assert.assertTrue(globalSearchPage.clickSearchButton(), "Unable to search by primary topic");
	}
	
	private void setMaterialitySettings(int rowNum) {
		// get materiality page
		Assert.assertTrue(materialityPage.getMaterialityPage(), "Unable to navigate to materiality page");
		
		Map<String, String> materialitySettings = UtilLib.readSpecificRow("", "materiality_data.xlsx", "MaterialitySettings", rowNum);
		
		boolean operationFlag = materialitySettings.entrySet().stream().allMatch(entry -> {
			String propertySectionName = entry.getKey().split("\\.")[0];
			String propertyName = entry.getKey().split("\\.")[1];
			if(entry.getValue().equals("Material")) {
				return materialityPage.setPropertyToMaterial(propertySectionName, propertyName);
			}else if(entry.getValue().equals("Immaterial")){
				return materialityPage.setPropertyToImmaterial(propertySectionName, propertyName);
			}
			return false;
		});
		
		// set materiality settings
		Assert.assertTrue(operationFlag, "Unable to set materiality settings");
		
		// update materiality
		Assert.assertTrue(materialityPage.submitMateriality(), "Unable to update materiality settings");
	}
	
	private void setClusterMateriality(int rowNum) {
		Map<String, String> materialitySettings = new LinkedHashMap<>();
		if(rowNum > 0) {
			materialitySettings = UtilLib.readSpecificRow("", "materiality_data.xlsx", "ClusterMateriality", rowNum);
		}
		System.out.println(materialitySettings);
		
		// select materiality statuses
		Assert.assertTrue(materialityPage.saveChanges(materialitySettings, null), "Unable to save cluster changes");
		
		// click submit button
		Assert.assertTrue(materialityPage.submitSaveChanges(), "Unable to submit cluster changes");
		
		// verify update message
		Assert.assertTrue(materialityPage.isClusterSaved(), "Cluster updates not saved");
	}
	
	private void setRequirementMateriality(int rowNum) {
		Map<String, String> materialitySettings = new LinkedHashMap<>();
		if(rowNum > 0) {
			materialitySettings = UtilLib.readSpecificRow("", "materiality_data.xlsx", "RequirementMateriality", rowNum);
		}
		System.out.println(materialitySettings);
		
		// select materiality statuses
		Assert.assertTrue(materialityPage.saveChanges(materialitySettings, null), "Unable to save requirement changes");
		
		// click submit button
		Assert.assertTrue(materialityPage.submitRequirementChanges(), "Unable to submit requirement changes");
		
		// verify update message
		Assert.assertTrue(materialityPage.isRequirementSaved(), "Requirement updates not saved");
	}
	
	private void CMDRefershResults() {
		try {
			Thread.sleep(2000);
			Assert.assertTrue(materialityPage.clickRefershResults());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
