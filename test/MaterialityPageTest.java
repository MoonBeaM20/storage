package com.wk.proviso.testscripts;

import java.awt.AWTException;
import java.util.Arrays;
import java.util.Collections;
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

public class MaterialityPageTestV2 extends ProvisoControls {

	private String internalUser = "testAdminUser@wolterskluwer.com";
	private String externalUser = "admin.expert@abcbank.com";
	private String password = "Test@123";
	
	private LoginPage loginPage = null;
	private GlobalSearchPage globalSearchPage = null;
	private MaterialityPage materialityPage = null;
	
	@BeforeMethod
	public void setup() throws AWTException {
		_webcontrols.get().resetImplicitWait("Set implicit wait", 10);
		loginPage = new LoginPage(_webcontrols);
		globalSearchPage = new GlobalSearchPage(_webcontrols);
		materialityPage = new MaterialityPage(_webcontrols);
	}
	
	@Test
	public void tc_01() throws AWTException {
		String testCaseId = "TC_01";
		String testDescription = "Verify User able to get Material alert in CMD when Substantive as Material and Non-Substantive as Material under Change Impact and compare with Cluster Attributes";
		String primaryTopic = "Limitations on Fees and Charges (Misc.)";
		String clusterName = "testm";
		String materiality = "Material";
		test_1_3(testCaseId, testDescription, primaryTopic, clusterName, materiality, 1, 1);
	}
	
	@Test
	public void tc_02() throws AWTException {
		String testCaseId = "TC_02";
		String testDescription = "Verify User able to get Material alert in CMD when Substantive as Material and Non-Substantive as Immaterial under Change Impact and compare with Cluster Attributes";
		String primaryTopic = "Limitations on Fees and Charges (Misc.)";
		String clusterName = "testm";
		String materiality = "Material";
		test_1_3(testCaseId, testDescription, primaryTopic, clusterName, materiality, 2, 2);
	}
	
	@Test
	public void tc_03() throws AWTException {
		String testCaseId = "TC_03";
		String testDescription = "Verify User able to get Material alert in CMD when Substantive as Immaterial and Non-Substantive as Material under Change Impact and compare with Cluster Attributes";
		String primaryTopic = "Limitations on Fees and Charges (Misc.)";
		String clusterName = "testm";
		String materiality = "Material";
		test_1_3(testCaseId, testDescription, primaryTopic, clusterName, materiality, 3, 3);
	}
	
	private void test_1_3(String testCaseId, String testDescription, String primaryTopic, String clusterName, 
			String materiality, int materialityRowNum, int clusterSubstantivityRowNum) throws AWTException {
		Reporting.getLogger().log(LogStatus.INFO, "<b>Test Case ID: "+testCaseId+"</b>", "<b>"+testDescription+"</b>");
		
		// login
		Assert.assertTrue(loginPage.loginToProViso(externalUser, password), "Unable to login");
		// set materiality
		setMaterialitySettings(UtilLib.readSpecificRow("", "materiality_data.xlsx", "MaterialitySettings", materialityRowNum));
		// logout and login
		logoutAndLogin(internalUser, password);
		// search by primary topic
		searchByPrimaryTopic(primaryTopic);
		// get cluster page
		Assert.assertTrue(materialityPage.getClusterPage(), "Unable to get cluster page");
		// click edit cluster icon
		Assert.assertTrue(materialityPage.clickEditCluster(clusterName), "Unable to click edit cluster icon");
		// edit cluster fields
		editClusterFileds();
		// save cluster changes
		saveClusterChanges(UtilLib.readSpecificRow("", "materiality_data.xlsx", "ClusterSubstantivity", clusterSubstantivityRowNum), "Do not suppress");
		// logout and login
		logoutAndLogin(externalUser, password);
		//verify materiality
		verifyMateriality(clusterName, "Cluster Update", materiality, true);
		
		Reporting.getLogger().log(LogStatus.INFO, "<b>Test Case ID: "+testCaseId+"</b>","<b>End test case</b>");	
	}
	
	@Test
	public void tc_04() throws AWTException {
		String testCaseId = "TC_04";
		String testDescription = "Verify User able to get Material alert in CMD when Substantive as Material and Non-Substantive as Material under Change Impact and compare with Requirement Applicability";
		String primaryTopic = "Do-Not-Call policies";
		String requirementName = "Do not call - js 2";
		String requirementApplicability = "Yes";
		String clusterName = "1930 1";
		String clusterImpact = "Requirement Update";
		String materiality = "Material";
		test_4_6(testCaseId, testDescription, primaryTopic, requirementName, true, requirementApplicability, clusterName, clusterImpact, materiality, 4, 4);
	}
	
	@Test
	public void tc_05() throws AWTException {
		String testCaseId = "TC_05";
		String testDescription = "Verify User able to get Material alert in CMD when Substantive as Material and Non-Substantive as Immaterial under Change Impact and compare with Requirement Applicability";
		String primaryTopic = "Do-Not-Call policies";
		String requirementName = "Do not call - js 2";
		String requirementApplicability = "No";
		String clusterName = "1930 1";
		String clusterImpact = "Requirement Update";
		String materiality = "Material";
		test_4_6(testCaseId, testDescription, primaryTopic, requirementName, true, requirementApplicability, clusterName, clusterImpact, materiality, 5, 5);
	}
	
	@Test
	public void tc_06() throws AWTException {
		String testCaseId = "TC_06";
		String testDescription = "Verify User able to get Material alert in CMD when Substantive as Immaterial and Non-Substantive as Material under Change Impact and compare with Requirement Applicability";
		String primaryTopic = "Do-Not-Call policies";
		String requirementName = "Do not call - js 2";
		String requirementApplicability = "Yes";
		String clusterName = "1930 1";
		String clusterImpact = "Requirement Update";
		String materiality = "Material";
		test_4_6(testCaseId, testDescription, primaryTopic, requirementName, true, requirementApplicability, clusterName, clusterImpact, materiality, 6, 6);
	}
	
	private void test_4_6(String testCaseId, String testDescription, String primaryTopic, String requirementName,
			boolean changeApplicability, String requirementApplicability, String clusterName, String clusterImpact, String materiality, 
			int materialityRowNum, int requirementSubstantivityRowNum) throws AWTException {
		Reporting.getLogger().log(LogStatus.INFO, "<b>Test Case ID: "+testCaseId+"</b>", "<b>"+testDescription+"</b>");
		
		// login
		Assert.assertTrue(loginPage.loginToProViso(externalUser, password), "Unable to login");
		// set materiality
		setMaterialitySettings(UtilLib.readSpecificRow("", "materiality_data.xlsx", "MaterialitySettings", materialityRowNum));
		//change applicability
		if(changeApplicability) {
			// wait for message to disappear
			Assert.assertTrue(materialityPage.isMaterialityPopupMessageInvisible(), "Materiality message still visible");
			// search by primary topic
			searchByPrimaryTopic(primaryTopic);
			// get requirement page
			Assert.assertTrue(materialityPage.getRequirementPage(), "Unable to get requirement page");
			// change requirement applicability
			Assert.assertTrue(materialityPage.changeApplicability(requirementName, requirementApplicability, Arrays.asList("Product Not Offered")));
		}
		// logout and login
		logoutAndLogin(internalUser, password);
		// search by primary topic
		searchByPrimaryTopic(primaryTopic);
		// get requirement page
		Assert.assertTrue(materialityPage.getRequirementPage(), "Unable to get requirement page");
		// select requirement to edit
		Assert.assertTrue(materialityPage.selectRequirementToEdit(requirementName), "Unable to select requirment to edit");
		// edit requirement fields
		editRequirementFields();
		// save requirement changes
		saveRequirementChanges(UtilLib.readSpecificRow("", "materiality_data.xlsx", "RequirementSubstantivity", requirementSubstantivityRowNum), "Do not suppress");
		// get cluster page
		Assert.assertTrue(materialityPage.getClusterPage(), "Unable to get cluster page");
		// click edit cluster
		Assert.assertTrue(materialityPage.clickEditCluster(clusterName), "Unable to click edit cluster");
		// set cluster review status
		Assert.assertTrue(materialityPage.setClusterReviewStatus("Approved"), "Unable to set review status");
		// save cluster changes
		saveClusterChanges(Collections.emptyMap(), "Do not suppress");
		// external login
		logoutAndLogin(externalUser, password);
		// verify materiality
		verifyMateriality(requirementName, clusterImpact, materiality, true);
		
		Reporting.getLogger().log(LogStatus.INFO, "<b>Test Case ID: "+testCaseId+"</b>","<b>End test case</b>");
	}
	
	@Test
	public void tc_07() throws AWTException {
		String testCaseId = "TC_07";
		String testDescription = "Verify User able to get Material alert in CMD when Substantive as Material and Non-Substantive as Material under Change Impact and compare with Requirement Attributes";
		String primaryTopic = "Do-Not-Call policies";
		String requirementName = "Do not call - js 2";
		String clusterName = "1930 1";
		String clusterImpact = "Requirement Update";
		String materiality = "Material";
		test_7_9(testCaseId, testDescription, primaryTopic, requirementName, clusterName, clusterImpact, materiality, 7, 7);
	}
	
	@Test
	public void tc_08() throws AWTException {
		String testCaseId = "TC_08";
		String testDescription = "Verify User able to get Material alert in CMD when Substantive as Material and Non-Substantive as Immaterial under Change Impact and compare with Requirement Attributes";
		String primaryTopic = "Do-Not-Call policies";
		String requirementName = "Do not call - js 2";
		String clusterName = "1930 1";
		String clusterImpact = "Requirement Update";
		String materiality = "Material";
		test_7_9(testCaseId, testDescription, primaryTopic, requirementName, clusterName, clusterImpact, materiality, 8, 8);
	}
	
	@Test
	public void tc_09() throws AWTException {
		String testCaseId = "TC_09";
		String testDescription = "Verify User able to get Material alert in CMD when Substantive as Immaterial and Non-Substantive as Material under Change Impact and compare with Requirement Attributes";
		String primaryTopic = "Do-Not-Call policies";
		String requirementName = "Do not call - js 2";
		String clusterName = "1930 1";
		String clusterImpact = "Requirement Update";
		String materiality = "Material";
		test_7_9(testCaseId, testDescription, primaryTopic, requirementName, clusterName, clusterImpact, materiality, 9, 9);
	}
	
	private void test_7_9(String testCaseId, String testDescription, String primaryTopic, String requirementName,
			String clusterName, String clusterImpact, String materiality, 
			int materialityRowNum, int requirementSubstantivityRowNum) throws AWTException {
		test_4_6(testCaseId, testDescription, primaryTopic, requirementName, false, "Yes", clusterName, clusterImpact, materiality, materialityRowNum, requirementSubstantivityRowNum);
	}
	
	@Test
	public void tc_10() throws AWTException {
		String testCaseId = "TC_10";
		String testDescription = "Verify User able to get Material alert in CMD for New-Adopted (Mirror Cluster)";
		String primaryTopic = "Do-Not-Call policies";
		String requirementToMove = "Compliance with National Do Not Call Registry";
		String clusterName = "1930 1";
		String clusterImpact = "Requirement Added";
		String materiality = "Material";
		test_10_11(testCaseId, testDescription, primaryTopic, requirementToMove, clusterName, clusterImpact, materiality, 10, 10);
	}
	
	@Test
	public void tc_11() throws AWTException {
		String testCaseId = "TC_11";
		String testDescription = "Verify User able to get Material alert in CMD for New - Unassigned (In a WK cluster that was made into custom clusters or in an unadopted WK cluster)";
		String primaryTopic = "Do-Not-Call policies";
		String requirementToMove = "Unsolicited Fax Advertisements and Honoring Opt-Out Requests";
		String clusterName = "A R 1";
		String clusterImpact = "Requirement Added";
		String materiality = "Material";
		test_10_11(testCaseId, testDescription, primaryTopic, requirementToMove, clusterName, clusterImpact, materiality, 11, 11);
	}
	
	private void test_10_11(String testCaseId, String testDescription, String primaryTopic, String requirementToMove, 
			String clusterName, String clusterImpact, String materiality,
			int materialitySettingsRowNum, int requirementSubstantivityRowNum) throws AWTException {
		Reporting.getLogger().log(LogStatus.INFO, "<b>Test Case ID: "+testCaseId+"</b>", "<b>"+testDescription+"</b>");
		
		// login
		Assert.assertTrue(loginPage.loginToProViso(externalUser, password), "Unable to login");
		// set materiality
		setMaterialitySettings(UtilLib.readSpecificRow("", "materiality_data.xlsx", "MaterialitySettings", materialitySettingsRowNum));
		// logout and login
		logoutAndLogin(internalUser, password);
		// search by primary topic
		searchByPrimaryTopic(primaryTopic);
		// get requirement page
		Assert.assertTrue(materialityPage.getClusterPage(), "Unable to get cluster page");
		// expand cluster
		Assert.assertTrue(materialityPage.viewClusterRequirements("Unclustered Requirement"), "Unable to click cluster expand icon");
		// move requirement
		Assert.assertTrue(materialityPage.moveRequirement(requirementToMove, clusterName), "Unable to move requirement");
		// save changes
		saveChanges(Collections.emptyMap(), "Do not suppress");
		// verify changes
		Assert.assertTrue(materialityPage.isRequirementMoved(), "Requirement not moved");
		// click edit cluster
		Assert.assertTrue(materialityPage.clickEditCluster(clusterName), "Unable to click edit cluster");
		// set cluster review status
		Assert.assertTrue(materialityPage.setClusterReviewStatus("Approved"), "Unable to set review status");
		// save cluster
		saveClusterChanges(Collections.emptyMap(), "Do not suppress");
		// external login
		logoutAndLogin(externalUser, password);
		// verify materiality on CMD notifications page
		verifyMateriality(clusterName, clusterImpact, materiality, true);
		
		Reporting.getLogger().log(LogStatus.INFO, "<b>Test Case ID: "+testCaseId+"</b>","<b>End test case</b>");
		
		//logout and login
		logoutAndLogin(internalUser, password);
		// undo requirement move
		undoRequirementMove(primaryTopic, requirementToMove, clusterName);
	}
	
	@Test
	public void tc_12() throws AWTException {
		String testCaseId = "TC_12";
		String testDescription = "Verify User able to get Material alert in CMD for Existing - Adopted (In a mirror or custom cluster)";
		String primaryTopic = "Do-Not-Call policies";
		String requirementName = "Do not call - js 2";
		String clusterName = "1930 1";
		String clusterImpact = "Requirement Update";
		String materiality = "Material";
		test_12_13(testCaseId, testDescription, primaryTopic, requirementName, clusterName, clusterImpact, materiality, 12, 12);
	}
	
	@Test
	public void tc_13() throws AWTException {
		String testCaseId = "TC_12";
		String testDescription = "Verify User able to get Material alert in CMD for Existing - Adopted (In a mirror or custom cluster)";
		String primaryTopic = "Do-Not-Call policies";
		String requirementName = "1 this is testing requirement - js 3";
		String clusterName = "A R 1";
		String clusterImpact = "Requirement Update";
		String materiality = "Material";
		test_12_13(testCaseId, testDescription, primaryTopic, requirementName, clusterName, clusterImpact, materiality, 13, 13);
	}
	
	private void test_12_13(String testCaseId, String testDescription, String primaryTopic, String requirementName,
			String clusterName, String clusterImpact, String materiality, 
			int materialityRowNum, int requirementSubstantivityRowNum) throws AWTException {
		test_4_6(testCaseId, testDescription, primaryTopic, requirementName, false, "Yes", clusterName, clusterImpact, materiality, materialityRowNum, requirementSubstantivityRowNum);
	}
	
	@Test
	public void tc_16() throws AWTException {
		String testCaseId = "TC_16";
		String testDescription = "Verify User able to get Immaterial alert in CMD when Substantive as Immaterial and Non-Substantive as Immaterial under Change Impact and compare with Cluster Attributes";
		String primaryTopic = "Limitations on Fees and Charges (Misc.)";
		String clusterName = "testm";
		String materiality = "Immaterial";
		test_16_18(testCaseId, testDescription, primaryTopic, clusterName, materiality, 16, 16);
	}
	
	@Test
	public void tc_17() throws AWTException {
		String testCaseId = "TC_17";
		String testDescription = "Verify User able to get Immaterial alert in CMD when Substantive as material and Non-Substantive as Immaterial under Change Impact and compare with Cluster Attributes";
		String primaryTopic = "Limitations on Fees and Charges (Misc.)";
		String clusterName = "testm";
		String materiality = "Immaterial";
		test_16_18(testCaseId, testDescription, primaryTopic, clusterName, materiality, 17, 17);
	}
	
	@Test
	public void tc_18() throws AWTException {
		String testCaseId = "TC_18";
		String testDescription = "Verify User able to get Immaterial alert in CMD when Substantive as Immaterial and Non-Substantive as material under Change Impact and compare with Cluster Attributes";
		String primaryTopic = "Limitations on Fees and Charges (Misc.)";
		String clusterName = "testm";
		String materiality = "Immaterial";
		test_16_18(testCaseId, testDescription, primaryTopic, clusterName, materiality, 18, 18);
	}
	
	private void test_16_18(String testCaseId, String testDescription, String primaryTopic, String clusterName, 
			String materiality, int materialityRowNum, int clusterSubstantivityRowNum) throws AWTException {
		test_1_3(testCaseId, testDescription, primaryTopic, clusterName, materiality, materialityRowNum, clusterSubstantivityRowNum);
	}
	
	@Test
	public void tc_19() throws AWTException {
		String testCaseId = "TC_19";
		String testDescription = "Verify User able to get Immaterial alert in CMD when Substantive as Immaterial and Non-Substantive as Immaterial under Change Impact and compare with Requirement Applicability";
		String primaryTopic = "Do-Not-Call policies";
		String requirementName = "Do not call - js 2";
		String requirementApplicability = "Yes";
		String clusterName = "1930 1";
		String clusterImpact = "Requirement Update";
		String materiality = "Immaterial";
		test_19_21(testCaseId, testDescription, primaryTopic, requirementName, true, requirementApplicability, clusterName, clusterImpact, materiality, 19, 19);
	}
	
	@Test
	public void tc_20() throws AWTException {
		String testCaseId = "TC_20";
		String testDescription = "Verify User able to get Immaterial alert in CMD when Substantive as Material and Non-Substantive as Immaterial under Change Impact and compare with Requirement Applicability";
		String primaryTopic = "Do-Not-Call policies";
		String requirementName = "Do not call - js 2";
		String requirementApplicability = "Yes";
		String clusterName = "1930 1";
		String clusterImpact = "Requirement Update";
		String materiality = "Immaterial";
		test_19_21(testCaseId, testDescription, primaryTopic, requirementName, true, requirementApplicability, clusterName, clusterImpact, materiality, 20, 20);
	}
	
	@Test
	public void tc_21() throws AWTException {
		String testCaseId = "TC_21";
		String testDescription = "Verify User able to get Immaterial alert in CMD when Substantive as Immaterial and Non-Substantive as Material under Change Impact and compare with Requirement Applicability";
		String primaryTopic = "Do-Not-Call policies";
		String requirementName = "Do not call - js 2";
		String requirementApplicability = "Yes";
		String clusterName = "1930 1";
		String clusterImpact = "Requirement Update";
		String materiality = "Immaterial";
		test_19_21(testCaseId, testDescription, primaryTopic, requirementName, true, requirementApplicability, clusterName, clusterImpact, materiality, 21, 21);
	}
	
	private void test_19_21(String testCaseId, String testDescription, String primaryTopic, String requirementName,
			boolean changeApplicability, String requirementApplicability, String clusterName, String clusterImpact, String materiality, 
			int materialityRowNum, int requirementSubstantivityRowNum) throws AWTException {
		test_4_6(testCaseId, testDescription, primaryTopic, requirementName, changeApplicability, requirementApplicability, clusterName, clusterImpact, materiality, materialityRowNum, requirementSubstantivityRowNum);
	}
	
	@Test
	public void tc_22() throws AWTException {
		String testCaseId = "TC_22";
		String testDescription = "Verify User able to get Immaterial alert in CMD when Substantive as Immaterial and Non-Substantive as Immaterial under Change Impact and compare with Requirement Attributes";
		String primaryTopic = "Do-Not-Call policies";
		String requirementName = "Do not call - js 2";
		String clusterName = "1930 1";
		String clusterImpact = "Requirement Update";
		String materiality = "Immaterial";
		test_22_24(testCaseId, testDescription, primaryTopic, requirementName, clusterName, clusterImpact, materiality, 22, 22);
	}
	
	@Test
	public void tc_23() throws AWTException {
		String testCaseId = "TC_23";
		String testDescription = "Verify User able to get Immaterial alert in CMD when Substantive as Material and Non-Substantive as Immaterial under Change Impact and compare with Requirement Attributes";
		String primaryTopic = "Do-Not-Call policies";
		String requirementName = "Do not call - js 2";
		String clusterName = "1930 1";
		String clusterImpact = "Requirement Update";
		String materiality = "Immaterial";
		test_22_24(testCaseId, testDescription, primaryTopic, requirementName, clusterName, clusterImpact, materiality, 22, 22);
	}
	
	@Test
	public void tc_24() throws AWTException {
		String testCaseId = "TC_24";
		String testDescription = "Verify User able to get Immaterial alert in CMD when Substantive as Immaterial and Non-Substantive as Material under Change Impact and compare with Requirement Attributes";
		String primaryTopic = "Do-Not-Call policies";
		String requirementName = "Do not call - js 2";
		String clusterName = "1930 1";
		String clusterImpact = "Requirement Update";
		String materiality = "Immaterial";
		test_22_24(testCaseId, testDescription, primaryTopic, requirementName, clusterName, clusterImpact, materiality, 22, 22);
	}
	
	private void test_22_24(String testCaseId, String testDescription, String primaryTopic, String requirementName,
			String clusterName, String clusterImpact, String materiality, 
			int materialityRowNum, int requirementSubstantivityRowNum) throws AWTException {
		test_4_6(testCaseId, testDescription, primaryTopic, requirementName, false, "Yes", clusterName, clusterImpact, materiality, materialityRowNum, requirementSubstantivityRowNum);
	}
	
	@Test
	public void tc_25() throws AWTException {
		String testCaseId = "TC_25";
		String testDescription = "Verify User able to get Immaterial alert in CMD for New-Adopted (Mirror Cluster)";
		String primaryTopic = "Do-Not-Call policies";
		String requirementToMove = "Compliance with National Do Not Call Registry";
		String clusterName = "1930 1";
		String clusterImpact = "Requirement Added";
		String materiality = "Immaterial";
		test_25_26(testCaseId, testDescription, primaryTopic, requirementToMove, clusterName, clusterImpact, materiality, 25, 25);
	}
	
	@Test
	public void tc_26() throws AWTException {
		String testCaseId = "TC_26";
		String testDescription = "Verify User able to get Immaterial alert in CMD for New - Unassigned (In a WK cluster that was made into custom clusters or in an unadopted WK cluster)";
		String primaryTopic = "Do-Not-Call policies";
		String requirementToMove = "Unsolicited Fax Advertisements and Honoring Opt-Out Requests";
		String clusterName = "A R 1";
		String clusterImpact = "Requirement Added";
		String materiality = "Immaterial";
		test_25_26(testCaseId, testDescription, primaryTopic, requirementToMove, clusterName, clusterImpact, materiality, 26, 26);
	}
	
	private void test_25_26(String testCaseId, String testDescription, String primaryTopic, String requirementToMove, 
			String clusterName, String clusterImpact, String materiality,
			int materialitySettingsRowNum, int requirementSubstantivityRowNum) throws AWTException {
		test_10_11(testCaseId, testDescription, primaryTopic, requirementToMove, clusterName, clusterImpact, materiality, materialitySettingsRowNum, requirementSubstantivityRowNum);
	}
	
	@Test
	public void tc_27() throws AWTException {
		String testCaseId = "TC_27";
		String testDescription = "Verify User able to get Immaterial alert in CMD for Existing - Adopted (In a mirror or custom cluster)";
		String primaryTopic = "Do-Not-Call policies";
		String requirementName = "Do not call - js 2";
		String clusterName = "1930 1";
		String clusterImpact = "Requirement Update";
		String materiality = "Immaterial";
		test_27_28(testCaseId, testDescription, primaryTopic, requirementName, clusterName, clusterImpact, materiality, 27, 27);
	}
	
	@Test
	public void tc_28() throws AWTException {
		String testCaseId = "TC_28";
		String testDescription = "Verify User able to get Immaterial alert in CMD for Existing - Unassigned (In a WK cluster that is unadopted or only partially adopted and this requirement was not placed in a custom cluster.)";
		String primaryTopic = "Do-Not-Call policies";
		String requirementName = "1 this is testing requirement - js 3";
		String clusterName = "A R 1";
		String clusterImpact = "Requirement Update";
		String materiality = "Immaterial";
		test_27_28(testCaseId, testDescription, primaryTopic, requirementName, clusterName, clusterImpact, materiality, 28, 28);
	}
	
	private void test_27_28(String testCaseId, String testDescription, String primaryTopic, String requirementName,
			String clusterName, String clusterImpact, String materiality, 
			int materialityRowNum, int requirementSubstantivityRowNum) throws AWTException {
		test_4_6(testCaseId, testDescription, primaryTopic, requirementName, false, "Yes", clusterName, clusterImpact, materiality, materialityRowNum, requirementSubstantivityRowNum);
	}
	
	@Test
	public void tc_31() throws AWTException {
		String testCaseId = "TC_31";
		String testDescription = "Verify User should not be able to view immaterial status in CMD";
		String primaryTopic = "Limitations on Fees and Charges (Misc.)";
		String clusterName = "nottoday";
		String materiality = "Immaterial";
		test_31(testCaseId, testDescription, true, primaryTopic, clusterName, materiality, 31, 31);
	}
	
	private void test_31(String testCaseId, String testDescription, boolean autoCompleteAllImmaterialAlerts, String primaryTopic, String clusterName, 
			String materiality, int materialityRowNum, int clusterSubstantivityRowNum) throws AWTException {
		Reporting.getLogger().log(LogStatus.INFO, "<b>Test Case ID: "+testCaseId+"</b>", "<b>"+testDescription+"</b>");
		
		// login
		Assert.assertTrue(loginPage.loginToProViso(externalUser, password), "Unable to login");
		// set materiality
		setMaterialitySettings(UtilLib.readSpecificRow("", "materiality_data.xlsx", "MaterialitySettings", materialityRowNum), autoCompleteAllImmaterialAlerts);
		// logout and login
		logoutAndLogin(internalUser, password);
		// search by primary topic
		searchByPrimaryTopic(primaryTopic);
		// get cluster page
		Assert.assertTrue(materialityPage.getClusterPage(), "Unable to get cluster page");
		// click edit cluster icon
		Assert.assertTrue(materialityPage.clickEditCluster(clusterName), "Unable to click edit cluster icon");
		// edit cluster fields
		editClusterFileds();
		// set review status to approved
		Assert.assertTrue(materialityPage.setClusterReviewStatus("Approved"), "Unable to set review status");
		// save cluster changes
		saveClusterChanges(UtilLib.readSpecificRow("", "materiality_data.xlsx", "ClusterSubstantivity", clusterSubstantivityRowNum), "Do not suppress");
		// logout and login
		logoutAndLogin(externalUser, password);
		//verify materiality
		Assert.assertTrue(materialityPage.viewCMDNotifications(), "Unable to navigate to CMD notifications page");
		CMDRefershResults();
		Assert.assertFalse(materialityPage.verifyMateriality(clusterName, "Cluster Update", materiality), "Invalid materiality");
		
		Reporting.getLogger().log(LogStatus.INFO, "<b>Test Case ID: "+testCaseId+"</b>","<b>End test case</b>");
	}
	
	@Test
	public void tc_32() throws AWTException {
		String testCaseId = "TC_32";
		String testDescription = "Verify User should be able to view immaterial status in CMD";
		String primaryTopic = "Limitations on Fees and Charges (Misc.)";
		String clusterName = "nottoday";
		String materiality = "Immaterial";
		test_32(testCaseId, testDescription, false, primaryTopic, clusterName, materiality, 32, 32);
	}
	
	private void test_32(String testCaseId, String testDescription, boolean autoCompleteAllImmaterialAlerts, String primaryTopic, String clusterName, 
			String materiality, int materialityRowNum, int clusterSubstantivityRowNum) throws AWTException {
		Reporting.getLogger().log(LogStatus.INFO, "<b>Test Case ID: "+testCaseId+"</b>", "<b>"+testDescription+"</b>");
		
		// login
		Assert.assertTrue(loginPage.loginToProViso(externalUser, password), "Unable to login");
		// set materiality
		setMaterialitySettings(UtilLib.readSpecificRow("", "materiality_data.xlsx", "MaterialitySettings", materialityRowNum), autoCompleteAllImmaterialAlerts);
		// logout and login
		logoutAndLogin(internalUser, password);
		// search by primary topic
		searchByPrimaryTopic(primaryTopic);
		// get cluster page
		Assert.assertTrue(materialityPage.getClusterPage(), "Unable to get cluster page");
		// click edit cluster icon
		Assert.assertTrue(materialityPage.clickEditCluster(clusterName), "Unable to click edit cluster icon");
		// edit cluster fields
		editClusterFileds();
		// set review status to approved
		Assert.assertTrue(materialityPage.setClusterReviewStatus("Approved"), "Unable to set review status");
		// save cluster changes
		saveClusterChanges(UtilLib.readSpecificRow("", "materiality_data.xlsx", "ClusterSubstantivity", clusterSubstantivityRowNum), "Do not suppress");
		// logout and login
		logoutAndLogin(externalUser, password);
		//verify materiality
		verifyMateriality(clusterName, "Cluster Update", materiality, true);
		
		Reporting.getLogger().log(LogStatus.INFO, "<b>Test Case ID: "+testCaseId+"</b>","<b>End test case</b>");
	}
	
	@Test
	public void tc_39() throws AWTException {
		String testCaseId = "TC_39";
		String testDescription = "Verify user should be able to view CMD notification for Subsection reference by selecting Do not suppress option";
		String primaryTopic = "Do-Not-Call policies";
		String requirementId = "75355";
		String requirementName = "Do not call - js 2";
		String requirementBulkActionOperationType = "Update Sub-section reference";
		String notificationOption = "Do not suppress";
		String clusterName = "1930 1";
		String materiality = "Material";
		test_39_41(testCaseId, testDescription, primaryTopic, requirementId, requirementName, requirementBulkActionOperationType, notificationOption, clusterName, materiality, true, 39, 39);
	}
	
	@Test
	public void tc_40() throws AWTException {
		String testCaseId = "TC_40";
		String testDescription = "Verify user should not be able to view CMD notification for Subsection reference by selecting suppress CMD notification option";
		String primaryTopic = "Do-Not-Call policies";
		String requirementId = "75587";
		String requirementName = "1 this is testing requirement - js 3";
		String clusterName = "A R 1";
		String requirementBulkActionOperationType = "Update Sub-section reference";
		String notificationOption = "Suppress CMD notifications";
		String materiality = "Material";
		test_39_41(testCaseId, testDescription, primaryTopic, requirementId, requirementName, requirementBulkActionOperationType, notificationOption, clusterName, materiality, false, 39, 39);
	}
	
	@Test
	public void tc_41() throws AWTException {
		String testCaseId = "TC_41";
		String testDescription = "Verify user should not be able to view CMD notification for Subsection reference by selecting Suppress requirement version creation";
		String primaryTopic = "Do-Not-Call policies";
		String requirementId = "75587";
		String requirementName = "1 this is testing requirement - js 3";
		String clusterName = "A R 1";
		String requirementBulkActionOperationType = "Update Sub-section reference";
		String notificationOption = "Suppress requirement version creation";
		String materiality = "Material";
		test_39_41(testCaseId, testDescription, primaryTopic, requirementId, requirementName, requirementBulkActionOperationType, notificationOption, clusterName, materiality, false, 39, 39);
	}
	
	private void test_39_41(String testCaseId, String testDescription, String primaryTopic, String requirementId, String requirementName, 
			String requirementBulkActionOperationType, String notificationOption, String clusterName, 
			String materiality, boolean shouldRecordAppear, int materialityRowNum, int requirementSubstantivityRowNum) throws AWTException {
		Reporting.getLogger().log(LogStatus.INFO, "<b>Test Case ID: "+testCaseId+"</b>", "<b>"+testDescription+"</b>");
		
		// login
		Assert.assertTrue(loginPage.loginToProViso(externalUser, password), "Unable to login");
		// set materiality
		setMaterialitySettings(UtilLib.readSpecificRow("", "materiality_data.xlsx", "MaterialitySettings", materialityRowNum));
		// logout and login
		logoutAndLogin(internalUser, password);
		// search by primary topic
		searchByPrimaryTopic(primaryTopic);
		// get bulk action page
		Assert.assertTrue(materialityPage.getBulkActionPage(), "Unable to get bulk action page");
		// switch to requirements tab
		Assert.assertTrue(materialityPage.switchToBulkActionTab("Requirement"), "Unable to switch tab");
		// select entity type
		Assert.assertTrue(materialityPage.selectRequirementEntityType("Requirement"), "Unable to select entity type");
		// enter requirement id
		Assert.assertTrue(materialityPage.enterTextInBulkActionTextarea(requirementId), "Unable to enter requirement id");
		// click go button
		Assert.assertTrue(materialityPage.clickGoButton(), "Unable to click go button");
		// click requirement check box
		Assert.assertTrue(materialityPage.selectRequirement(requirementName));
		// perform bulk operation
		performRequirementBulkAction(requirementBulkActionOperationType, requirementSubstantivityRowNum, notificationOption);
		// get cluster page
		Assert.assertTrue(materialityPage.getClusterPage(), "Unable to get cluster page");
		// click edit cluster
		Assert.assertTrue(materialityPage.clickEditCluster(clusterName), "Unable to click edit cluster");
		// set review status to approved
		Assert.assertTrue(materialityPage.setClusterReviewStatus("Approved"), "Unable to set review status");
		// click save button
		Assert.assertTrue(materialityPage.clickClusterSaveButton(), "Unable to click save button");
		// verify message
		Assert.assertTrue(materialityPage.isClusterSaved(), "Cluster changes not saved");
		// logout and login
		logoutAndLogin(externalUser, password);
		//verify materiality
		verifyMateriality(requirementName, "Requirement Update", materiality, shouldRecordAppear);
	}
	
	@Test
	public void tc_42() throws AWTException {
		String testCaseId = "TC_42";
		String testDescription = "Verify user should be able to view CMD notification for Meta data by selecting Do not suppress option";
		String primaryTopic = "Do-Not-Call policies";
		String requirementId = "75355";
		String requirementName = "Do not call - js 2";
		String clusterName = "1930 1";
		String requirementBulkActionOperationType = "Update Metadata";
		String notificationOption = "Do not suppress";
		String materiality = "Material";
		test_42_44(testCaseId, testDescription, primaryTopic, requirementId, requirementName, requirementBulkActionOperationType, notificationOption, clusterName, materiality, true, 42, 42);
	}
	
	@Test
	public void tc_43() throws AWTException {
		String testCaseId = "TC_43";
		String testDescription = "Verify user should be able to view CMD notification for Meta data by selecting  Suppress requirement version creation";
		String primaryTopic = "Do-Not-Call policies";
		String requirementId = "75587";
		String requirementName = "1 this is testing requirement - js 3";
		String clusterName = "A R 1";
//		String requirementId = "75355";
//		String requirementName = "Do not call - js 2";
//		String clusterName = "1930 1";
		String requirementBulkActionOperationType = "Update Metadata";
		String notificationOption = "Suppress requirement version creation";
		String materiality = "Material";
		test_42_44(testCaseId, testDescription, primaryTopic, requirementId, requirementName, requirementBulkActionOperationType, notificationOption, clusterName, materiality, false, 43, 43);
	}
	
	@Test
	public void tc_44() throws AWTException {
		String testCaseId = "TC_44";
		String testDescription = "Verify user should be able to view CMD notification for Meta data by selecting  Suppress CMD notifications";
		String primaryTopic = "Do-Not-Call policies";
		String requirementId = "75587";
		String requirementName = "1 this is testing requirement - js 3";
		String clusterName = "A R 1";
		String requirementBulkActionOperationType = "Update Metadata";
		String notificationOption = "Suppress CMD notifications";
		String materiality = "Material";
		test_42_44(testCaseId, testDescription, primaryTopic, requirementId, requirementName, requirementBulkActionOperationType, notificationOption, clusterName, materiality, false, 44, 44);
	}
	
	private void test_42_44(String testCaseId, String testDescription, String primaryTopic, String requirementId, String requirementName, 
			String requirementBulkActionOperationType, String notificationOption, String clusterName, 
			String materiality, boolean shouldRecordAppear, int materialityRowNum, int requirementSubstantivityRowNum) throws AWTException {
		Reporting.getLogger().log(LogStatus.INFO, "<b>Test Case ID: "+testCaseId+"</b>", "<b>"+testDescription+"</b>");
		
		// login
		Assert.assertTrue(loginPage.loginToProViso(externalUser, password), "Unable to login");
		// set materiality
		setMaterialitySettings(UtilLib.readSpecificRow("", "materiality_data.xlsx", "MaterialitySettings", materialityRowNum));
		// logout and login
		logoutAndLogin(internalUser, password);
		// search by primary topic
		searchByPrimaryTopic(primaryTopic);
		// get bulk action page
		Assert.assertTrue(materialityPage.getBulkActionPage(), "Unable to get bulk action page");
		// switch to requirements tab
		Assert.assertTrue(materialityPage.switchToBulkActionTab("Requirement"), "Unable to switch tab");
		// select entity type
		Assert.assertTrue(materialityPage.selectRequirementEntityType("Requirement"), "Unable to select entity type");
		// enter requirement id
		Assert.assertTrue(materialityPage.enterTextInBulkActionTextarea(requirementId), "Unable to enter requirement id");
		// click go button
		Assert.assertTrue(materialityPage.clickGoButton(), "Unable to click go button");
		// click requirement check box
		Assert.assertTrue(materialityPage.selectRequirement(requirementName));
		// perform bulk operation
		performRequirementBulkAction(requirementBulkActionOperationType, requirementSubstantivityRowNum, notificationOption);
		// get cluster page
		Assert.assertTrue(materialityPage.getClusterPage(), "Unable to get cluster page");
		// click edit cluster
		Assert.assertTrue(materialityPage.clickEditCluster(clusterName), "Unable to click edit cluster");
		// set review status to approved
		Assert.assertTrue(materialityPage.setClusterReviewStatus("Approved"), "Unable to set review status");
		// save changes
		saveClusterChanges(Collections.emptyMap(), "Do not supress");
		// verify message
		Assert.assertTrue(materialityPage.isClusterSaved(), "Cluster changes not saved");
		// logout and login
		logoutAndLogin(externalUser, password);
		//verify materiality
		verifyMateriality(requirementName, "Requirement Update", materiality, shouldRecordAppear);
	}
	
	
	// helper methods
	
	private void editClusterFileds() {
		int randomNumber = ThreadLocalRandom.current().nextInt(100);
		System.out.println(randomNumber);
		
		// enter release comments
		Assert.assertTrue(materialityPage.enterClusterReleaseComments("release comments " + randomNumber), "Unable to enter release comments");
		// enter grouping explanation
		Assert.assertTrue(materialityPage.enterClusterGroupingExplanation("grouping explanation " + randomNumber), "Unable to enter grouping explanation");
		// enter federal law citation compared
		Assert.assertTrue(materialityPage.enterClusterFederalLawCitationCompared("federal law citation compared " + randomNumber), "Unable to enter federal law citation compared");
		// enter comparative federal state analysis
		Assert.assertTrue(materialityPage.enterClusterComparativeFederalStateAnalysis("comparative federal state analysis " + randomNumber), "Unable to enter comparative federal state analysis");
	}
	
	private void saveClusterChanges(Map<String, String> substantivityStatuses, String notificationOption) {
		// click save button
		Assert.assertTrue(materialityPage.clickClusterSaveButton(), "Unable to click save button");
		// save changes
		saveChanges(substantivityStatuses, notificationOption);
		// verify message
		Assert.assertTrue(materialityPage.isClusterSaved(), "Cluster changes not saved");
	}
	
	private void editRequirementFields() {
		int randomNumber = ThreadLocalRandom.current().nextInt(100);
		System.out.println(randomNumber);
	
		// enter requirement summary
		Assert.assertTrue(materialityPage.enterRequirementSummary("new legal information " + randomNumber), "Unable to enter requirement summary");
		// select party
//		Assert.assertTrue(materialityPage.selectParty("Individual"), "Unable to set party");
		// enter requirement notes
		Assert.assertTrue(materialityPage.enterRequirementNotes("Requirment notes " + randomNumber), "Unable to enter requirement notes");
		// enter release notes
		Assert.assertTrue(materialityPage.enterReleaseNotes("New release notes " + randomNumber), "Unable to enter release notes");
		// select change type
		Assert.assertTrue(materialityPage.selectChangeType("WK Change", "DATA1"), "Unable to select change type");
		// select change type
		Assert.assertTrue(materialityPage.selectChangeType("Reg Change", "BLI"), "Unable to select change type");
	}
	
	private void performRequirementBulkAction(String requirementBulkActionOperationType, int requirementSubstantivityRowNum, String notificationOption) {
		int randomNumber = ThreadLocalRandom.current().nextInt(100);
		System.out.println(randomNumber);
		
		Map<String, String> requriementSubstantivity = UtilLib.readSpecificRow("", "materiality_data.xlsx", "RequirementSubstantivity", requirementSubstantivityRowNum);
		System.out.println(requriementSubstantivity);
		switch (requirementBulkActionOperationType) {
			case "Update Sub-section reference":
				Assert.assertTrue(materialityPage.clickUpdateSubSectionReferenceButton(), "Unable to click update sub section reference button");
				Assert.assertTrue(materialityPage.setSubstantivity(requriementSubstantivity), "Unable to set substantivity");
				Assert.assertTrue(materialityPage.setBulkActionNotificationOption(notificationOption), "Unable to set notification option");
				if(notificationOption != "Do not suppress")
					Assert.assertTrue(materialityPage.enterBulkActionSuppressionComments("Supression comments"));
				Assert.assertTrue(materialityPage.selectBulkRequirementsChangeType("WK Change", "DATA1"), "Unable to select change type");
				Assert.assertTrue(materialityPage.selectBulkRequirementsChangeType("Reg Change", "BLI"), "Unable to select change type");
				Assert.assertTrue(materialityPage.enterSaveChangesReleaseComments("Bulk action release notes " + randomNumber), "Unable to enter release notes");
				Assert.assertTrue(materialityPage.submitBulkActionChanges(), "Unable to submit changes");
				Assert.assertTrue(materialityPage.isUpdateSubSectionReferenceSuccessful(), "Sub section reference not updated");
				break;
			case "Update Metadata":
				Assert.assertTrue(materialityPage.clickUpdateMetadataButton(), "Unable to click update meta data button");
				Assert.assertTrue(materialityPage.enterRequirementMetadataRequirementSummary("Bulk action legal information " + randomNumber), "Unable to enter requirement summary");
				Assert.assertTrue(materialityPage.enterRequirementMetadataRequirementNotes("Bulk action requirement notes " + randomNumber), "Unable to enter requirement notes");
				Assert.assertTrue(materialityPage.enterRequirementMetadataReleaseNotes("Bulk action release note " + randomNumber), "Unable to enter release notes");
				Assert.assertTrue(materialityPage.clickRequirementMetadataUpdateToAllButton(), "Unable to click upadte to all");
				Assert.assertTrue(materialityPage.setSubstantivity(requriementSubstantivity), "Unable to set substantivity");
				Assert.assertTrue(materialityPage.setBulkActionNotificationOption(notificationOption), "Unable to set notification option");
				if(notificationOption != "Do not suppress")
					Assert.assertTrue(materialityPage.enterBulkActionSuppressionComments("Supression comments"));
				Assert.assertTrue(materialityPage.selectBulkRequirementsChangeType("WK Change", "DATA1"), "Unable to select change type");
				Assert.assertTrue(materialityPage.selectBulkRequirementsChangeType("Reg Change", "BLI"), "Unable to select change type");
				Assert.assertTrue(materialityPage.submitBulkActionChanges(), "Unable to submit changes");
				Assert.assertTrue(materialityPage.isRequirementMetadataUpdateSuccessful(), "Sub section reference not updated");
				break;
			case "Update Tags":
				Assert.assertTrue(materialityPage.clickUpdateTagsButton(), "Unable to click update tags button");
				Assert.assertTrue(materialityPage.addTag("Topic", "Do-Not-Call policies"), "Unable to add tag");
				Assert.assertTrue(materialityPage.enterSaveChangesReleaseComments("Bulk action release notes " + randomNumber), "Unable to enter release notes");
				Assert.assertTrue(materialityPage.setSubstantivity(requriementSubstantivity), "Unable to set substantivity");
				Assert.assertTrue(materialityPage.setBulkActionNotificationOption(notificationOption), "Unable to set notification option");
				if(notificationOption != "Do not suppress")
					Assert.assertTrue(materialityPage.enterBulkActionSuppressionComments("Supression comments"));
				Assert.assertTrue(materialityPage.selectBulkRequirementsChangeType("WK Change", "DATA1"), "Unable to select change type");
				Assert.assertTrue(materialityPage.selectBulkRequirementsChangeType("Reg Change", "BLI"), "Unable to select change type");
				Assert.assertTrue(materialityPage.submitBulkActionChanges(), "Unable to submit changes");
				Assert.assertTrue(materialityPage.isUpdateTagsSuccessful(), "Tags not added");
				break;
			default:
				break;
		}
	}
	
	private void saveRequirementChanges(Map<String, String> substantivityStatuses, String notificationOption) {
		// click save button
		Assert.assertTrue(materialityPage.clickClusterSaveButton(), "Unable to click save button");
		// save changes
		saveChanges(substantivityStatuses, notificationOption);
		// verify message
		Assert.assertTrue(materialityPage.isRequirementSaved(), "Requirement changes not saved");
	}
	
	private void saveChanges(Map<String, String> substantivityStatuses, String notificationOption) {
		// set substantivity statuses
		Assert.assertTrue(materialityPage.setSubstantivity(substantivityStatuses), "Unable to set substantivity statuses");
		// set notification option
		Assert.assertTrue(materialityPage.setNotificationOption(notificationOption));
		// click submit button
		Assert.assertTrue(materialityPage.submitSaveChanges(), "Unable to submit cluster changes");
	}
	
	private void searchByPrimaryTopic(String primaryTopic) {
		// click search drop down
		Assert.assertTrue(globalSearchPage.clickSearchDropDown(), "Unable to click search drop down");
		// select primary topic
		Assert.assertTrue(globalSearchPage.selectPrimaryTopic(primaryTopic), "Unable to select primary topic");
		// click search button
		Assert.assertTrue(globalSearchPage.clickSearchButton(), "Unable to click search button");
	}
	
	private void undoRequirementMove(String primaryTopic, String requirementName, String clusterName) throws AWTException {
		// search by primary topic
		searchByPrimaryTopic(primaryTopic);
		// get cluster page
		Assert.assertTrue(materialityPage.getClusterPage(), "Unable to get cluster page");
		// click expand icon
		Assert.assertTrue(materialityPage.viewClusterRequirements(clusterName), "Unable to click expand icon");
		// move requirement
		Assert.assertTrue(materialityPage.moveRequirement(requirementName, "Unclustered Requirements"), "Unable to move requirement");
		// save changes
		saveChanges(Collections.emptyMap(), "");
		// verify message
		Assert.assertTrue(materialityPage.isRequirementMoved(), "Requirement not moved");
		// click edit cluster
		Assert.assertTrue(materialityPage.clickEditCluster(clusterName), "Unable to click edit cluster");
		// set review status
		Assert.assertTrue(materialityPage.setClusterReviewStatus("Approved"), "Unable to set review status");
		// save cluster changes
		saveClusterChanges(Collections.emptyMap(), "");
	}
	
	private void setMaterialitySettings(Map<String, String> materialitySettings) {
		// get materiality page
		Assert.assertTrue(materialityPage.getMaterialityPage(), "Unable to navigate to materiality page");
		
		boolean operationFlag = materialitySettings.entrySet().stream().skip(1).allMatch(entry -> {
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
	
	private void setMaterialitySettings(Map<String, String> materialitySettings, boolean autoCompleteAllImmaterialAlerts) {
		// get materiality page
		Assert.assertTrue(materialityPage.getMaterialityPage(), "Unable to navigate to materiality page");
		
		// set auto complete all immaterial alerts
		Assert.assertTrue(materialityPage.setAutocompleteImmaterialAlerts(autoCompleteAllImmaterialAlerts), "Unable to set auto complete all immaterial alerts");
		
		boolean operationFlag = materialitySettings.entrySet().stream().skip(1).allMatch(entry -> {
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
	
	private void verifyMateriality(String changeRecord, String clusterImpact, String materiality, boolean shouldRecordAppear) {
		Assert.assertTrue(materialityPage.viewCMDNotifications(), "Unable to navigate to CMD notifications page");
		CMDRefershResults();
		if(shouldRecordAppear) {
			Assert.assertTrue(materialityPage.verifyMateriality(changeRecord, clusterImpact, materiality), "Invalid materiality");
		}else {
			Assert.assertFalse(materialityPage.verifyMateriality(changeRecord, clusterImpact, materiality), "Invalid materiality");
		}
	}
	
	private void CMDRefershResults() {
		try {
			Thread.sleep(6000);
			Assert.assertTrue(materialityPage.clickRefershResults());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void logoutAndLogin(String username, String password) throws AWTException {
		// logout
		Assert.assertTrue(loginPage.logOutFromProViso(), "Unable to logout");
		//login
		Assert.assertTrue(loginPage.loginToProViso(username, password), "Unable to login");
	}
	
}
