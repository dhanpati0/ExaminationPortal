package com.csmtech.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.csmtech.bean.AnswerBean;
import com.csmtech.bean.QuestionBean;
//import com.csmtech.beanMongo.AnswerBeanMongo;
import com.csmtech.dto.MyDto;
import com.csmtech.exporter.CandidateExcelExporter;
import com.csmtech.model.Answer;
import com.csmtech.model.Candidate;
import com.csmtech.model.Configure;
import com.csmtech.model.CorrectAnswer;
import com.csmtech.model.Reason;
import com.csmtech.model.SubTest;
import com.csmtech.model.SubTestTaker;
import com.csmtech.model.TestTaker;
import com.csmtech.model.User;
import com.csmtech.repository.CandidateRepository;
import com.csmtech.service.AnswerService;
import com.csmtech.service.CandidateService;
import com.csmtech.service.ConfigureService;
import com.csmtech.service.CorrectAnswerService;
import com.csmtech.service.QuestionService;
import com.csmtech.service.QuestionSubTestService;
import com.csmtech.service.ReasonService;
import com.csmtech.service.SubTestService;
import com.csmtech.service.SubTestTakerService;
import com.csmtech.service.TestService;
import com.csmtech.service.TestTakerService;
//import com.csmtech.serviceMongo.TempAnsSaveservice;
import com.csmtech.util.EmailService;

@Controller
@RequestMapping("exam")
public class CandidateController {

	@PersistenceContext
	private EntityManager entityManager;

//	@Autowired
//	private TempAnsSaveservice ansSaveservice;

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private ReasonService reasonService;

	@Autowired
	private CandidateService candService;

	@Autowired
	private TestTakerService testTakerService;

	@Autowired
	private SubTestTakerService subTestTakerService;

	@Autowired
	private TestService testService;

	@Autowired
	private SubTestService subTestService;

	@Autowired
	private ConfigureService configureService;

	@Autowired
	private QuestionSubTestService questionSubTestService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private AnswerService answerService;
	@Autowired
	private CorrectAnswerService correctAnswerService;

	@Autowired
	private CandidateRepository candRepo;
	
	@Autowired
	private CandidateService candidateService;
	
	private List<QuestionBean> savedQuestions;

	private static final Logger logger = LoggerFactory.getLogger(CandidateController.class);

	@GetMapping("/testTakers")
	public String testTakers(Model model) {
		logger.info("CandidateController Getting test takers...");
		User user = (User) this.httpSession.getAttribute("sessionData");
		model.addAttribute("username", user.getName());
		// model.addAttribute("testtakerLists", testTakerService.getAll());

		model.addAttribute("saveBox", "yes");

		//Integer invalCol = (Integer) httpSession.getAttribute("colValid");
		// System.out.println(invalCol);

		//String invalideColumnNames = (String) httpSession.getAttribute("invalideColumnNames");
		//model.addAttribute("invalidCol", invalCol);
		//model.addAttribute("invalideColumnNames", invalideColumnNames);
		model.addAttribute("testTakerList", testTakerService.getAllCollege());
		model.addAttribute("testList", testService.getAllTest());

		return "admin/testTakerPage";

	}

	@PostMapping("/addTest-Taker")
	public String addTestTaker(@RequestParam(name = "testTakerName") String testTakerName, Model model,
			RedirectAttributes rd) {
		logger.info("Adding test taker: {}", testTakerName);
		TestTaker testTaker = new TestTaker();
		testTaker.setTestTakerName(testTakerName);
		TestTaker t = testTakerService.save(testTaker);
		logger.info("testTaker::" + t);
		// System.out.println("testTaker::" + t);
		model.addAttribute("testtakerLists", testTakerService.getAll());
		testService.getAllTest();
		return "admin/testTakerPage";
	}

	@GetMapping("gettestTakersList")
	public void getAllTestTakerList(HttpServletResponse resp) throws IOException {
		logger.info("Ajax for getting test taker list...");
		// System.out.println("ajax for get test Taker List");

		List<TestTaker> testtakerList = testTakerService.getAllCollege();
		logger.info("INSIDE 1st AJAX::" + testtakerList);
		// System.out.println("INSIDE 1st AJAX::" + testtakerList);
		String st = "";

		for (TestTaker x : testtakerList) {

			st = st + x.getTestTakerName() + "_" + x.getTestTakerId() + ",";

		}
		if (st.endsWith(",")) {
			st = st.substring(0, st.length() - 1);
		}
		resp.getWriter().print(st);
	}

	@PostMapping("/addSubTest-Taker")
	public void addSubTestTaker(@RequestParam(name = "testTakerName") TestTaker testTaker,
			@RequestParam(name = "SubTestTakerName") String subTestTaker, RedirectAttributes rd,

			HttpServletResponse resp, Model model) throws IOException {
		logger.info("Adding sub test taker: {} for test taker: {}", subTestTaker, testTaker.getTestTakerName());

		Integer testTakerId = testTaker.getTestTakerId();
		List<SubTestTaker> subTestTakerList = subTestTakerService.getAllSubTestTakerByTestTakerId(testTakerId);

		boolean subTakerExist = subTestTakerService.getAllSubTestTakerByTestTakerId(testTakerId).stream()
				.anyMatch(sub -> sub.getSubTestTakerName().equalsIgnoreCase(subTestTaker));
		PrintWriter writer = resp.getWriter();
		if (subTakerExist) {
			writer.print("exists");
		} else {
			SubTestTaker sub = new SubTestTaker();
			sub.setTestTaker(testTaker);
			sub.setSubTestTakerName(subTestTaker);
			subTestTakerService.save(sub);
			model.addAttribute("addressNav", "yes");
			model.addAttribute("testTakerList", testTakerService.getAllCollege());
			writer.print("notExists");
		}
	}

	// getSubTestTaker List
	@GetMapping("getSubtestTakerByTestTaker")
	public void getAllSubTestTakerList(HttpServletResponse resp,
			@RequestParam(name = "testTakerId") Integer testTakerId) throws IOException {
		logger.info("Ajax for getting sub test taker list for test taker ID: {}", testTakerId);

		// System.out.println("ajax for get Sub test Taker List" + testTakerId);

		List<SubTestTaker> subtesttakerList = subTestTakerService.getAllSubTestTakerByTestTakerId(testTakerId);
		System.out.println("INSIDE 1st AJAX::" + subtesttakerList);
		String st = "";

		for (SubTestTaker x : subtesttakerList) {

			st = st + x.getSubTestTakerName() + "_" + x.getSubTestTakerId() + ",";

		}
		if (st.endsWith(",")) {
			st = st.substring(0, st.length() - 1);
		}
		resp.getWriter().print(st);
	}

	@GetMapping("/addTestTakerByExcel")
	public String addTestTakerByExcel(Model model, @RequestParam("subTestTakerId") Integer subTestTakerId,
			HttpServletRequest req) {
		logger.info("Adding test taker by Excel with subTestTakerId: {}", subTestTakerId);

		User user = (User) this.httpSession.getAttribute("sessionData");
		model.addAttribute("username", user.getName());
		Integer testTakerId = (Integer) httpSession.getAttribute("testTakerData");
		System.out.println(subTestTakerId);
		httpSession.setAttribute("subTestTakerData", subTestTakerId);
		model.addAttribute("testtakerList", testTakerService.getAll());
		System.out.println(subTestTakerService.getAllSubTestTakerByTestTakerId(testTakerId));
		model.addAttribute("listSubTestTaker", subTestTakerService.getAllSubTestTakerByTestTakerId(testTakerId));
		System.out.println("++++++++" + candService.findCandidateBySubTestTakerId(subTestTakerId));
		model.addAttribute("listCandidates", candService.findCandidateBySubTestTakerId(subTestTakerId));
		model.addAttribute("ttId", testTakerId);
		model.addAttribute("stId", subTestTakerId);
		model.addAttribute("saveBoxExcel", "no");

		model.addAttribute("adressNav", "yes");
		model.addAttribute("navTestTaker", "yes");
		model.addAttribute("navSubTestTaker", "yes");
		req.getSession().setAttribute("SubTestTakerName",
				subTestTakerService.findSubTestTakerName(subTestTakerId).getSubTestTakerName());

		// for configure page
		model.addAttribute("testList", testService.getAllTest());

		return "admin/testTakerPage";

	}

	@GetMapping("/testTakerByExcel")
	public void showTestAndAddQuestionSet(@RequestParam(name = "subtestTakerId") Integer subtestTakerId,
			HttpServletResponse resp, Model model) throws IOException {
		logger.info("Showing test and adding question set for subtestTakerId: {}", subtestTakerId);

		// System.out.println("here subtest coming" + subtestTakerId);
		httpSession.setAttribute("stestDataId", subtestTakerId);
		model.addAttribute("saveBoxExcel", "no");
		System.out.println("here coming for add set");

		// model.addAttribute("SetDiv", "no");
//		model.addAttribute("test", "yes");
//		model.addAttribute("testNav", "yes");
//		model.addAttribute("Tname", "yes");
//		req.getSession().setAttribute("subTestNav", subTestService.findById(sId).getSubTestName());

		String st = "no";

		resp.getWriter().print(st);

	}

	@GetMapping("/getSubTestNameByTestId")
	public void getSubItemListByQuestionTypeId(@RequestParam(name = "tId") Integer testId, HttpServletResponse resp)
			throws IOException {

		PrintWriter pw = resp.getWriter();
		logger.info("AJAX for getting sub test names by test ID: {}", testId);

		// System.out.println("inside AJAX:::" + testId);

		List<SubTest> subtestList = subTestService.getAllSubTestByTestId(testId);
		System.out.println("++++++++++++++++" + subtestList);
		String t = "";
		t += "<option value='" + "select" + "'>" + "--select--" + "</option>";
		for (SubTest x : subtestList) {
			t += "<option value='" + x.getSubTestId() + "'>" + x.getSubTestName() + "</option>";
		}
		pw.print(t);

	}

	@PostMapping("/getCandidatesByExcel")
	public String saveCandidatesByExcel(@RequestParam("excelfile") MultipartFile candidateDetails, Model model,
			RedirectAttributes redirectAttrs) {

		boolean flag = true;
		logger.info("Saving candidates by Excel");
		Integer subTestTakerId = (Integer) httpSession.getAttribute("subTestTakerData");
		SubTestTaker subTestTaker = subTestTakerService.getSubTestTaker(subTestTakerId);
		logger.info("SubTestTaker " + subTestTaker);
		// System.out.println(subTestTaker);

		Pattern namePattern = Pattern.compile("^[a-zA-Z]+([\\s-][a-zA-Z]+)*$");
		Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
		Pattern mobilePattern = Pattern.compile("^[0-9]{10}$");

		int totalColumns = 0;
		String contentType = candidateDetails.getContentType();
		if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			DataFormatter dataFormatter = new DataFormatter();
			try (FileInputStream file = (FileInputStream) candidateDetails.getInputStream()) {
				logger.info("Reading Excel file and processing data...");
				XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(file);
				Row row;

				Sheet sheet = workbook.getSheetAt(0);
				totalColumns = sheet.getRow(0).getLastCellNum();
				List<String> invalideColumnNames = new ArrayList<String>();
				List<String> valideColumnNames = new ArrayList<String>();
				valideColumnNames.add("Candidate First Name");
				valideColumnNames.add("Candidate Last Name");
				valideColumnNames.add("Candidate Email");
				valideColumnNames.add("Candidate MobileNo");
				boolean invalide = false;
				Row header = sheet.getRow(0);
				String invalideNames = "Invalide Columns :";
				for (Cell cell : header) {
					String columnName = cell.getStringCellValue();
					// System.out.println("Column name: " + columnName);
					logger.info("Column name: {}", columnName);
					if (!valideColumnNames.contains(columnName)) {
						invalideColumnNames.add(columnName);

					}

				}

				for (String name : invalideColumnNames) {
					invalideNames = invalideNames + "," + name;
				}
				logger.info("Invalid Column names: {}", invalideNames);
				// System.out.println("Invalide Column names: " + invalideNames);

				List<String> columnHeaders = new ArrayList<>();
				Row headerRow = sheet.getRow(0);
				for (int columnIndex = 0; columnIndex < totalColumns; columnIndex++) {
					String headerValue = dataFormatter.formatCellValue(headerRow.getCell(columnIndex)).trim();
					columnHeaders.add(headerValue);
				}

				if (totalColumns != 4) {
					logger.error("Invalid Column count: {}", totalColumns);
					logger.info("Invalid Column count: {}", totalColumns);
					// System.out.println("inavlid Column" + totalColumns);
					//httpSession.setAttribute("colValid", totalColumns);
					redirectAttrs.addAttribute("colValid", totalColumns);
					//httpSession.setAttribute("invalideColumnNames", invalideNames);
					redirectAttrs.addFlashAttribute("invalideColumnNames", invalideNames);

				} else {
					invalideNames = "WRONG COLUMN NAMES :";
					header = sheet.getRow(0);
					invalideColumnNames.clear();
					for (Cell cell : header) {
						String columnName = cell.getStringCellValue();
						logger.info("Column name: {}", columnName);

						// System.out.println("Column name: " + columnName);
						if (!valideColumnNames.contains(columnName)) {
							invalideColumnNames.add(columnName);

						}

					}

					if (invalideColumnNames.size() > 0) {

						for (String name : invalideColumnNames) {
							invalideNames = invalideNames + "," + name;
						}
					}

					if (!"WRONG COLUMN NAMES :".equals(invalideNames)) {
						logger.info("Invalid Column names: {}", invalideNames);
						// System.out.println("Invalide Column names: " + invalideNames);
						httpSession.setAttribute("colValid", totalColumns);
						httpSession.setAttribute("invalideColumnNames", invalideNames);

					} else {
						logger.info("{} VALID COLUMN COMES......", totalColumns);

						// System.out.println(totalColumns + "VALID COLUMN COMES......");

						List<String> fnameList = new ArrayList<String>();
						List<String> lnameList = new ArrayList<String>();
						List<String> emailList = new ArrayList<String>();
						List<String> mobileNoList = new ArrayList<String>();

						for (int i = 1; i <= sheet.getLastRowNum(); i++) {
							row = sheet.getRow(i);
							Candidate cd = new Candidate();

							String fname = dataFormatter.formatCellValue(row.getCell(0)).trim();
							String lname = dataFormatter.formatCellValue(row.getCell(1)).trim();
							String email = dataFormatter.formatCellValue(row.getCell(2)).trim();
							String mobileNo = dataFormatter.formatCellValue(row.getCell(3)).trim();

							Matcher match = namePattern.matcher(fname);
							if (match.matches()) {
								logger.info("First Name field is valid: {}", fname);
								// System.out.println("firstName field is valide....");
							} else {
								logger.info("First Name field is invalid: {}", fname);
								// System.out.println("firstName field is invalide.....");
								fnameList.add(fname);
								redirectAttrs.addFlashAttribute("invalidFname", fname);
							}

							if (fnameList.size() > 0) {
								String wrongfnames = fnameList.toString();
								logger.info("Wrong First Names: {}", wrongfnames);
								logger.info("Database saving stopped due to invalid First Names.");
								// System.out.println("wrongfnames :" + wrongfnames);
								// System.out.println("DATABASE SAVING STOP DUE TO INVALIDE FNAME...");
								flag = false;
							}

							// for Last Name
							match = namePattern.matcher(lname);
							if (match.matches()) {
								logger.info("Last Name field is valid: {}", lname);
								// System.out.println("LastName field is valide....");
							} else {
								logger.info("Last Name field is invalid: {}", lname);
								// System.out.println("LastName field is invalide.....");
								lnameList.add(lname);
								redirectAttrs.addFlashAttribute("invalidLname", lname);

							}

							if (lnameList.size() > 0) {
								String wrongfnames = lnameList.toString();
								logger.info("Wrong Last Names: {}", wrongfnames);
								logger.info("Database saving stopped due to invalid Last Names.");
								// System.out.println("wrongfnames :" + wrongfnames);
								// System.out.println("DATABASE SAVING STOP DUE TO INVALIDE LastName...");
								flag = false;
							}

							// for email validation
							match = emailPattern.matcher(email);
							if (match.matches()) {
								logger.info("Email field is valid: {}", email);
								// System.out.println("email field is valide....");
							} else {
								logger.info("Email field is invalid: {}", email);
								// System.out.println("email field is invalide.....");
								emailList.add(email);
								redirectAttrs.addFlashAttribute("invalidemail", email);

							}

							if (emailList.size() > 0) {
								String wrongemails = emailList.toString();
								logger.info("Wrong Emails: {}", wrongemails);
								logger.info("Database saving stopped due to invalid Emails.");
								// System.out.println("wrongemail :" + wrongemails);
								// System.out.println("DATABASE SAVING STOP DUE TO INVALIDE EMAIL...");
								flag = false;
							}

							// FOR MOBILE NUMBER
							match = mobilePattern.matcher(mobileNo);
							if (match.matches()) {
								logger.info("Mobile Number field is valid: {}", mobileNo);
								// System.out.println("mobileNo field is valide....");
							} else {
								logger.info("Mobile Number field is invalid: {}", mobileNo);
								// System.out.println("mobileNo field is invalide.....");
								mobileNoList.add(mobileNo);
								redirectAttrs.addFlashAttribute("invalidmobileNo", mobileNo);

							}

							if (mobileNoList.size() > 0) {
								String wrongemails = mobileNoList.toString();
								logger.info("Wrong Email: {}", wrongemails);
								logger.info("Database saving stopped due to invalid Mobile Numbers.");
								// System.out.println("wrongemail :" + wrongemails);
								// System.out.println("DATABASE SAVING STOP DUE TO INVALIDE MobileNo...");
								flag = false;
							}

							if (flag) {

								Candidate cand = candService.getCandidateByEmail(email);
								logger.info("++++++++++++++++Candidate: {}", cand);
								// System.out.println("++++++++++++++++" + cand);
								if (cand != null) {
									logger.info("This Candidate is Present!!!");
									// System.out.println("This Candidate is Present!!!");
									String result = "error";
									redirectAttrs.addFlashAttribute("invalidemail", result);

								} else {
									logger.info("Database saving start");
									// System.out.println("database saving start");
									// If all validations pass, save the candidate
									cd.setCandFirstname(fname);
									cd.setCandLastname(lname);
									cd.setCandidateemail(email);
									cd.setCandpassword(fname + "@#");
									String encodeTxt = encodeText(cd.getCandFirstname() + "@#");
									cd.setCandpassword(encodeTxt);
									cd.setCandMobile(mobileNo);

									cd.setSubTestTaker(subTestTaker);
									cd.setIsdelete("No");
									cd.setStatus("inactive");
									cd.setPause("No");
									cd.setProgress("Not Started");
									candService.saveCandidate(cd);

									String result = "success";
									redirectAttrs.addFlashAttribute("result", result);

								}
							}

						}

					}

				}

			} catch (Exception e) {
				logger.error("An error occurred while processing Excel file", e);
				e.printStackTrace();
			}

		}

		return "redirect:./testTakers";

	}

	@GetMapping("/candidate/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		try {
			logger.info("Exporting candidates to Excel.");

			response.setContentType("application/octet-stream");
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			String currentDateTime = dateFormatter.format(new Date());

			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=FormatforAddCandidates" + currentDateTime + ".xlsx";
			response.setHeader(headerKey, headerValue);

			CandidateExcelExporter excelExporter = new CandidateExcelExporter();
			excelExporter.export(response);

			logger.info("Export completed successfully.");
		} catch (Exception e) {
			logger.error("Error exporting candidates to Excel.", e);
			throw new IOException("Error exporting candidates to Excel.", e);
		}
	}

	@PostMapping("/saveCandidate")
	public String saveCandidate(@RequestParam(name = "candFirstname") String candFirstname,
			@RequestParam(name = "candLastname") String candLastname,
			@RequestParam(name = "candidateemail") String candidateemail,
			@RequestParam(name = "candMobile") String candMobile, RedirectAttributes rd) {
		logger.info("Saving candidate: {}", candidateemail);
		Integer subTestTakerId = (Integer) httpSession.getAttribute("subTestTakerData");
		SubTestTaker subTestTaker = subTestTakerService.getSubTestTaker(subTestTakerId);
		System.out.println(subTestTaker);

		boolean candExist = candService.findAllCandidate().stream()
				.anyMatch(c -> c.getCandidateemail().equalsIgnoreCase(candidateemail));
		if (candExist) {
			rd.addFlashAttribute("candExist", "yes");

		} else {

			Candidate cand = new Candidate();
			cand.setCandFirstname(candFirstname);
			cand.setCandLastname(candLastname);
			cand.setCandidateemail(candidateemail);
			cand.setCandMobile(candMobile);
			cand.setCandpassword(candFirstname.trim() + "@#");
			String encodeTxt = encodeText(cand.getCandFirstname() + "@#");
			cand.setCandpassword(encodeTxt);
			cand.setStatus("inactive");
			cand.setIsdelete("No");
			cand.setPause("No");
			cand.setSubTestTaker(subTestTaker);
			cand.setProgress("Not Started");
			candService.saveCandidate(cand);
			rd.addFlashAttribute("candy", "yes");
			String DecodeText = decodeText(encodeTxt);
			System.out.println("DecodeText" + DecodeText);
		}
		return "redirect:./testTakers";
	}

	@PostMapping("/saveConfigure")
	public String saveConfigureTime(@RequestParam(name = "subtestName") SubTest subTest,
			@RequestParam(name = "noOfQuestion") Integer noQuestion, @RequestParam(name = "testDate") String testDate,
			@RequestParam(name = "loginTime") String loginTime, @RequestParam(name = "startTime") String startTime,
			@RequestParam(name = "endTime") String endTime, RedirectAttributes rd) throws IOException {
		logger.info("Saving configuration for SubTest: {}", subTest.getSubTestName());

		httpSession.setAttribute("noQuestionData", noQuestion);
		Integer subTestTakerId = (Integer) httpSession.getAttribute("subTestTakerData");
		SubTestTaker subTestTaker = subTestTakerService.getSubTestTaker(subTestTakerId);
		Configure presentConfigure = configureService.findConfigureBySubTestTakerId(subTestTakerId);
		if(presentConfigure!=null) {
			System.out.println("already configure");
			rd.addFlashAttribute("alreadyPresent", "This Batch is Alerady Configured!!");
			return "redirect:./testTakers";
		}else {
		Configure cf = new Configure();
		cf.setSubTest(subTest);
		cf.setTestDate(LocalDate.parse(testDate));
		cf.setLoginTime(LocalTime.parse(loginTime));
		LocalTime stTime = LocalTime.parse(startTime);
		LocalTime enTime = LocalTime.parse(endTime);
		cf.setStartTime(stTime);
		cf.setEndTime(enTime);
		long long1 = enTime.getLong(ChronoField.MINUTE_OF_DAY) - stTime.getLong(ChronoField.MINUTE_OF_DAY);
		cf.setTestDuration(LocalTime.of((int) long1 / 60, (int) long1 % 60));
		cf.setEnterNoQuestion(noQuestion);
		cf.setSubTestTaker(subTestTaker);
		Configure saveConfig = configureService.saveConfigure(cf);
		candidateService.saveConfigureToCandidates(subTestTakerId, saveConfig);
		candidateService.findCandidateBySubTestTakerIdAndSetTotalMark(subTestTakerId,noQuestion);
		//candidateService.saveTotalMark(existCand,noQuestion);
		//subTestTakerService.saveConfigureData(subTestTakerId, saveConfig);
		rd.addFlashAttribute("config", "yes");
		
		return "redirect:./testTakers";
		}
		
	}

	@GetMapping("checkQuestionAviableOrNot")
	public void checkQuestionAviableOrNot(@RequestParam(name = "testId") Integer tId,
			@RequestParam(name = "sId") Integer sId, HttpServletResponse resp, Model model) throws IOException {
		logger.info("Checking availability of questions for Test ID: {} and Subtest ID: {}", tId, sId);

		Integer totalQuestions = questionSubTestService.countAllQuestionBySubtestId(sId);
		logger.info("Total questions available: {}", totalQuestions + " you want to add :" + tId);

		// System.out.println(totalQuestions + " you want to add :" + tId);
		model.addAttribute("totalQuestionsinSet", totalQuestions);
		resp.getWriter().print(totalQuestions);
	}

	@GetMapping("/sendEmailToCandidates")
	public String getCandidateBySubTestTakerId(Model model, RedirectAttributes rd) {
		logger.info("Sending emails to candidates.");
		// System.out.println("getting email");
		Integer subTestTakerId = (Integer) httpSession.getAttribute("subTestTakerData");
		List<Candidate> candidateList = candService.findCandidateBySubTestTakerId(subTestTakerId);
		Configure config = configureService.findConfigureBySubTestTakerId(subTestTakerId);
		EmailService.sendEmails(candidateList, config);
		rd.addFlashAttribute("emailSent", "yes");
		System.out.println(candService.findCandidateBySubTestTakerId(subTestTakerId));

		return "redirect:./testTakers";
	}

	@ResponseBody
	@GetMapping("/liveCandidateDetail")
	public List<String> getLiveStatus() {
		System.out.println("Inside Here to get Live Status");
		Candidate cand = (Candidate) httpSession.getAttribute("sessionData1");
		// System.out.println(candService.findDetailsById(cand.getCandid()));
		Reason reasonOfCan = reasonService.findByCandidateId(cand.getCandid());
		if (reasonOfCan != null) {
			List<String> s = new ArrayList<String>();
			s.add(reasonService.findByCandidateId(cand.getCandid()).getReason());
			s.add(candService.findDetailsById(cand.getCandid()).getProgress());
			// System.out.println("Here is the List - >"+s);
			return s;
		} else
			return null;
	}

	@PostMapping("/candidateQuestion")
	public String getQuestion(RedirectAttributes rd, HttpServletRequest req, Model model) throws ParseException {
		logger.info("Getting questions for candidate.");
		Candidate cand = (Candidate) httpSession.getAttribute("sessionData1");
		Integer cid = cand.getCandid();
		logger.info("candidateId" + cid);

		Configure config = configureService.findConfigureBySubTestTakerId(cand.getSubTestTaker().getSubTestTakerId());
		LocalDateTime of = LocalDateTime.of(config.getTestDate(), config.getLoginTime());
		long examLogin = of.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		LocalDateTime of1 = LocalDateTime.of(config.getTestDate(), config.getStartTime());
		long startExam = of1.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		LocalDateTime of2 = LocalDateTime.of(config.getTestDate(), config.getEndTime());
		long endExam = of2.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		long currentTime = System.currentTimeMillis();
		HashMap<String, Long> mp = new HashMap<>();
		mp.put("examLogin", examLogin);
		mp.put("startExam", startExam);
		mp.put("endExam", endExam);
		mp.put("currentTime", currentTime);
		model.addAllAttributes(mp);
		logger.info("Here is the Config details -> "+config);
		// Change------------------------------------------------

		Integer sId = config.getSubTest().getSubTestId();
		Integer noQuestion = config.getEnterNoQuestion();
		logger.info("-------------------Here is the noOfQuestion ->"+noQuestion);
		Integer answerCount = 0;
		logger.info("nnnn" + answerCount);

		// Integer remainingCount = noQuestion - answerCount;

		// System.out.println("rrrrrr" + remainingCount);

		if (cand.getStatus().equals("1")) { // 1 states that the candidate is active
			System.out.println("herre i am");
			answerCount = answerService.getAnswerByCandidateId(cid);
			// List<QuestionBean> randomQuest =
			// questionSubTestService.getAllRemainingQuestions(noQuestion, sId, cid);
			// System.out.println(randomQuest + "//////");
			// Collections.shuffle(randomQuest);

			if ("Logout".equals(cand.getProgress().trim())) {
				List<Answer> answeredQuestions = answerService.findByCandidate(cid);
				answerCount = answerService.getAnswerByCandidateId(cid);
				savedQuestions.forEach((i) -> System.out.println(i.getQuestionText()));
				ArrayList<Integer> listOfIDS = new ArrayList<Integer>();
//				List<QuestionBean> questionAfterLogout = new ArrayList<QuestionBean>();
//				Set<Integer> answeredQuestionIds = new HashSet<Integer>();
//				for (Answer a : answeredQuestions) {
//					answeredQuestionIds.add(a.getQuestion());
//				}
//
//				for (QuestionBean q : savedQuestions) {
//					for (Integer i : answeredQuestionIds) {
//						if (q.getQuestionId() == i) {
//							questionAfterLogout.add(q);
//						}
//					}
//
//				}
//				Integer remainingCount = noQuestion - answerCount;
//				List<QuestionBean> randomQuestions = questionSubTestService
//						.findQuestionsRandomlyByGivingAdminInput(remainingCount, sId);
//				for (QuestionBean q : randomQuestions) {
//					questionAfterLogout.add(q);
//				}

				logger.info(" we are get the question");
				for (QuestionBean q : savedQuestions) {
					List<CorrectAnswer> allAnswerByQuestionId = correctAnswerService
							.getAllAnswerByQuestionId(q.getQuestionId());
					if (allAnswerByQuestionId.size() > 1) {
						listOfIDS.add(q.getQuestionId());
						System.out.println(listOfIDS);
						req.setAttribute("listOfIDS", listOfIDS);
					}

				}
				httpSession.setAttribute("answeredQuestions", answeredQuestions);
				httpSession.setAttribute("quests", savedQuestions);
				httpSession.setAttribute("noQuestion", noQuestion);
			}

			else {
				List<QuestionBean> randomQuestions = questionSubTestService
						.findQuestionsRandomlyByGivingAdminInput(noQuestion, sId);
				// answerCount = answerService.getAnswerByCandidateId(cid);
				savedQuestions = randomQuestions;
				System.out.println("here to random question" + randomQuestions);
				Collections.shuffle(randomQuestions);
				httpSession.setAttribute("quests", savedQuestions);
				httpSession.setAttribute("noQuestion", 0);
			//	logger.info(" answeredQuestions -> "+answeredQuestions);
				httpSession.setAttribute("answeredQuestions", new ArrayList<Answer>());
				randomQuestions.forEach((i) -> System.out.println("---------------------------------" + i));
				// model.addAttribute("questions", randomQuestions.get(0));
				ArrayList<Integer> listOfIDS = new ArrayList<Integer>();
				for (QuestionBean q : randomQuestions) {
					List<CorrectAnswer> allAnswerByQuestionId = correctAnswerService
							.getAllAnswerByQuestionId(q.getQuestionId());
					if (allAnswerByQuestionId.size() > 1) {
						// ArrayList<Integer> listOfIDS = new ArrayList<Integer>();
						listOfIDS.add(q.getQuestionId());
						System.out.println(listOfIDS);
						req.setAttribute("listOfIDS", listOfIDS);
					}
				}
			}

			cand.setStatus("1");
			LocalTime t = LocalTime.now();
			cand.setCandStartTime(t);
			cand.setProgress("Started");
			candService.saveCandidate(cand);
			return "candidate/questions";
		} else {
			rd.addFlashAttribute("TimeOut", "TimeFailed");
			return "redirect:/exam/login";
		}

	}


	


	@GetMapping(value = "/getAnsById")
	public String getAns(@RequestParam(name = "qId") Integer qId) {
		logger.info("Getting answers for Question ID: {}", qId);

		String check = "";
		System.out.println("Here is the QID" + qId);
		List<CorrectAnswer> allAnswerByQuestionId = correctAnswerService.getAllAnswerByQuestionId(qId);
		// System.out.println("allAnswerByQuestionId" + allAnswerByQuestionId);
		for (CorrectAnswer c : allAnswerByQuestionId) {
			check += c.getCorrectAns();
		}
		logger.info("here is the concated String to use------------------------" + check);

		// System.out.println("here is the concated String to
		// use------------------------" + check);
		// return correctAnswerService.getAllAnswerByQuestionId(qId);
		return check;
	}

//	@PostMapping(value = "/tempsaveoption", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public void tempsaveOption(@RequestBody List<AnswerBeanMongo> answerBeanMongos) {
//		answerBeanMongos.forEach(t -> ansSaveservice.saveAnswerBean(t));
//		// System.out.println(b+"@Document@Document@Document@Document@Document@Document");
//	}

	@PostMapping(value = "/saveExam", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveExam(@RequestBody List<AnswerBean> answerBeans) {
		System.out.println("save exam");
		logger.info("Saving exam answers.");
		logger.info("AnswerBeans" + answerBeans);
		System.out.println("answerBeans" + answerBeans.get(0).getQuestionId());
		Integer qId = answerBeans.get(0).getQuestionId();
		Candidate candidate = (Candidate) httpSession.getAttribute("sessionData1");

		List<QuestionBean> questions = (List<QuestionBean>) httpSession.getAttribute("quests");
		List<Answer> checkAns = answerService.checkQuestionIsAvialableOrNot(qId, candidate.getCandid());
		if (!checkAns.isEmpty()) {
			answerService.deleteAnswerByQuestionId(qId, candidate.getCandid());
			System.out.println("answer deleted");
		}
		System.out.println(checkAns + "//////////");
		Integer noOfQuestion = questions.size();
		logger.info("NoOfQuestion NM::" + noOfQuestion);
		// System.out.println("NM::" + noOfQuestion);
		Double totalMark = (double) (noOfQuestion * 2);
		double seventyFivePercent = totalMark * 0.75;
		logger.info("75%%%%:::" + seventyFivePercent);
		System.out.println("75%%%%:::" + seventyFivePercent);
		Integer seventyFivePercentRounded = (int) Math.round(seventyFivePercent);
		Random random = new Random();
		logger.info("noOfQuestion:::" + noOfQuestion);
		logger.info("seventyFivePercentRounded***" + seventyFivePercentRounded);
		// System.out.println("noOfQuestion:::" + noOfQuestion);
		// System.out.println("seventyFivePercentRounded***" +
		// seventyFivePercentRounded);
		List<AnswerBean> answers = answerBeans;
		Answer answer = null;
		Integer marks = 0;
		Integer finalMarks = 0;
		Integer fMarks = 0;
		Integer u = 0;
		Integer[] ids = new Integer[answers.size()];
		if (answers != null) {
			for (AnswerBean i : answers) {
				if (u < answers.size()) {
					ids[u] = i.getQuestionId();
					u++;
				}

				logger.info("IDs in ARRAY IS HERE -----------= " + Arrays.toString(ids));
				// System.out.println("IDs in ARRAY IS HERE -----------= " +
				// Arrays.toString(ids));
				Integer tmarks = 0;
				List<CorrectAnswer> correctAnsById = correctAnswerService.getAllAnswerByQuestionId(i.getQuestionId());
				correctAnsById.toArray();
				logger.info("  *****^^^^^^^^^^^^^^^^^^^   " + Arrays.toString(correctAnsById.toArray()));
				// System.out.println(" *****^^^^^^^^^^^^^^^^^^^ " +
				// Arrays.toString(correctAnsById.toArray()));
				// String[] correctAnsInArray = new String[correctAnsById.size()];
//				System.out.println("Array fucntion is used -------------- " + correctAnsById.toArray());
//				System.out.println(" here is the array --- " + Arrays.toString(correctAnsInArray));
				CorrectAnswer[] myArray = new CorrectAnswer[correctAnsById.size()];
				correctAnsById.toArray(myArray);// HERE THE LIST OF CORRECTANS IS BEEN CONVERTED TO ARRAY
				logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" + myArray.length);

				// System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" +
				// myArray.length);
				String[] onlyCorrectAns = new String[myArray.length];
				for (int y = 0; y < myArray.length; y++) {
					onlyCorrectAns[y] = myArray[y].getCorrectAns();// HERE THE ARRAY OF CORRECTANS IS BEEN CONVERTED TO
																	// STRING OF getCorrectAns()
					// System.out.println(myArray[y].getCorrectAns()+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				}
				logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
						+ Arrays.toString(onlyCorrectAns));

				// System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+
				// Arrays.toString(onlyCorrectAns));
				String[] optionsFromCandidate = new String[i.getOption().length];

				for (int w = 0; w < i.getOption().length; w++) {
					optionsFromCandidate[w] = i.getOption()[w];// HERE THE LIST OF SELECTED OPTIONS IS BEEN CONVERTED TO
																// STRING OF OPTIONS
				}

				String toCmp = "";
				for (int r = 0; r < onlyCorrectAns.length; r++) {
					toCmp += onlyCorrectAns[r];
				}
				logger.info(toCmp + " String is used for comparison ");

				// System.out.println(toCmp + " String is used for comparison ");

				tmarks = 0;

				// System.out.println("if answer is available not");
				for (int e = 0; e < optionsFromCandidate.length; e++) {
					if (toCmp.contains(optionsFromCandidate[e])) {
						tmarks = 0;
						if (i.getOption().length == 1) {
							logger.info("LENGTH IS 1::");
							logger.info(tmarks + "your temp marks where lenght is equal to 1");
							// System.out.println("LENGTH IS 1::");
							// System.out.println(tmarks + "your temp marks where lenght is equal to 1");
							tmarks += 2;
							answer = new Answer();
							answer.setQuestion(i.getQuestionId());
							answer.setCandidate(candidate.getCandid());
							answer.setOptChoose(optionsFromCandidate[e]);
							answer.setStatus("correct");
							logger.info("Got 2 marks");
							// System.out.println("Got 2 marks");
						} else {
							logger.info("lenght is : " + i.getOption().length);
							logger.info(tmarks + "your temp marks where lenght is more than 1");
							// System.out.println("lenght is : " + i.getOption().length);
							// System.out.println(tmarks + "your temp marks where lenght is more than 1");
							tmarks += 1;
							logger.info("Got 1 marks");
							// System.out.println("Got 1 marks");
							answer = new Answer();
							answer.setQuestion(i.getQuestionId());
							answer.setCandidate(candidate.getCandid());
							answer.setOptChoose(optionsFromCandidate[e]);
							answer.setStatus("correct");
							logger.info(tmarks + "your temp marks");
							logger.info("LENGTH IS 1::ELSE");
							// System.out.println(tmarks + "your temp marks");
							// System.out.println("LENGTH IS 1::ELSE");
						}
						// marks = 0;
						if (tmarks % 2 <= 1 && tmarks > 0) {
							logger.info(" inside saving here");
							// System.out.println(" inside saving here");
							fMarks += tmarks;
							logger.info("GOT MARKS ----------+++++++++ ====" + fMarks);
							// System.out.println("GOT MARKS ----------+++++++++ ====" + fMarks);
							// marks += 2;
							answer.setMark(tmarks);
							answer.setStatus("correct");
							logger.info(answer + " ANSWER OBJECT IS HERE");
							// System.out.println(answer + " ANSWER OBJECT IS HERE");
							answerService.save(answer);
							// Answer ans = new Answer();
							// ans.setMark(1);
							logger.info(optionsFromCandidate[e] + "::yes correct");
							logger.info("******" + answer);
							// System.out.println(optionsFromCandidate[e] + "::yes correct");
							// System.out.println("******" + answer);
						} else {
							logger.info(" - only one option is correct");
							// System.out.println(" - only one option is correct");
						}

					} else {
						answer = new Answer();
						answer.setQuestion(i.getQuestionId());
						answer.setCandidate(candidate.getCandid());
						answer.setStatus("wrong");
						logger.info("TOTAL MARKS ----------+++++++++ ====" + totalMark);
						// System.out.println("TOTAL MARKS ----------+++++++++ ====" + totalMark);
						answer.setMark(0);
						logger.info(Arrays.toString(i.getOption())
								+ "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ is the option");
						// System.out.println(Arrays.toString(i.getOption())+
						// "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ is the option");
						answer.setOptChoose(optionsFromCandidate[e]);
						answer.setCandidate(candidate.getCandid());
						logger.info(answer + " ANSWER OBJECT IS HERE");

						// System.out.println(answer + " ANSWER OBJECT IS HERE");
						answerService.save(answer);
						logger.info(i.getQuestionId() + " WRONG ANSWER");
						// System.out.println(i.getQuestionId() + " WRONG ANSWER");
					}
				}
			}

			if (fMarks % 2 != 0) {
				fMarks--;
			}
			// }
		} else {
			logger.info("AnswerBean Is Null");
			// System.out.println("AnswerBean Is Null");
		}
		logger.info("Marks recieved ----- " + fMarks);
		logger.info("Total marks:------ " + totalMark);
		// System.out.println("Marks recieved ----- " + fMarks);
		// System.out.println("Total marks:------ " + totalMark);
		Integer[] rMarks = new Integer[answers.size()];
		for (int i = 0; i < ids.length; i++) {
			Integer tmarks = 0;
			List<Integer> marksById = answerService.getMarksById(ids[i]);
//			  for(int q=0;q<marksById.size();q++) {
//				  tmarks +=   ;
//			  }
			for (Integer inte : marksById) {
				logger.info("value of Integer is = " + inte);
				// System.out.println("value of Integer is = " + inte);
				tmarks += inte;
			}
			if (tmarks == 2) {
				finalMarks += tmarks;
			}

		}
		logger.info("Here is the Final marks ========= " + finalMarks);
		// System.out.println("Here is the Final marks ========= " + finalMarks);

		/*
		 * //candidate.setTotalMark(totalMark); candidate.setMarkAppear(finalMarks); if
		 * (finalMarks >= seventyFivePercentRounded) { logger.info("PASS"); //
		 * System.out.println("PASS"); candidate.setResultStatus("PASS"); } else {
		 * candidate.setResultStatus("FAIL"); logger.info("FAIL"); //
		 * System.out.println("FAIL"); } logger.info("*********" + candidate);
		 */
		System.out.println("*********" + candidate);
		// candService.saveCandidate(candidate);
		// You can perform further actions with the total marks if needed.

		return ResponseEntity.ok("Exam saved successfully.");
	}

	public static String decodeText(String encText) {
		try {
			byte[] result = Base64.getUrlDecoder().decode(encText);

			return new String(result);
		} catch (IllegalArgumentException e) {
			// Handle the error (e.g., log the error, provide a default value, etc.)
			logger.error("Error decoding text: {}", e.getMessage());
			e.printStackTrace();
			return null; // Or a default value indicating decoding failure
		}
	}

	public static String encodeText(String plainTxt) {

		// REverse the plaintext
		StringBuffer revereTxt = new StringBuffer(plainTxt);
		logger.debug("Reversed text: {}", revereTxt);
		logger.info("Reversed text: {}", revereTxt);
		// System.out.println(revereTxt);
		return Base64.getEncoder().encodeToString(revereTxt.toString().getBytes());

	}

	@GetMapping("getAllCandidatesByStId")
	public void getAllCandidatesBySubTestTakerId(@RequestParam(name = "subtestTakerId") Integer subtestTakerId,
			HttpServletResponse resp) throws IOException {
		logger.info("Getting all candidates for SubTestTaker ID: {}", subtestTakerId);

		// System.out.println(subtestTakerId + "////////////////////");

		httpSession.setAttribute("subTestTakerData", subtestTakerId);

		PrintWriter pw = resp.getWriter();
		logger.debug("Inside AJAX::: {}", subtestTakerId);
		// System.out.println("inside AJAX:::" + subtestTakerId);

		List<Candidate> candidates = candService.findCandidateBySubTestTakerId(subtestTakerId);
		logger.debug("Candidates: {}", candidates);
		logger.info("Candidates: {}", candidates);
		// System.out.println("++++++++++++++++" + candidates);
		String st = "";

		for (Candidate x : candidates) {

			st = st + x.getCandidateemail() + "_" + x.getCandid() + ",";

		}
		if (st.endsWith(",")) {
			st = st.substring(0, st.length() - 1);
		}
		resp.getWriter().print(st);

	}

	/**
	 * Our changes in this file goes from here.
	 */
	@PostMapping("/updatePauseState")
	public ResponseEntity<String> updatePauseState(@RequestParam String candidate_id, @RequestParam String newState) {
		logger.info("Updating pause state for Candidate ID: {} to state: {}", candidate_id, newState);

		Candidate candidate = candService.findDetailsById(Integer.parseInt(candidate_id));
		if (candidate == null) {
			logger.warn("Candidate not found for ID: {}", candidate_id);
			return ResponseEntity.badRequest().body("Candidate not found");

		} else {
			candidate.setPause(newState);
			candService.saveCandidate(candidate);
			logger.info("Updated successfully");
			return ResponseEntity.ok("Updated successfully");
		}
	}

	@PostMapping(value = "/updateCandEndTime")
	public ResponseEntity<String> updateCandEndTime(@RequestBody MyDto dto) {
		try {
			logger.info("Candidate ID:" + dto.getCandid());
			logger.info("Candidate End Time {}" + dto.getCandEndTime());
			// System.out.println(dto.getCandid());
			// System.out.println(dto.getCandEndTime());
			LocalTime time = dto.getCandEndTime();
			Optional<Candidate> o = candRepo.findById(dto.getCandid());
			Candidate candidate = o.get();

			if (candidate != null) {
				logger.info("Found Cand");
				// System.out.println("Found Cand");
				candidate.setCandEndTime(time);
				logger.info("Candidate: " + candidate);
				// System.out.println(candidate);
				// candidate.setStatus("inactive");
				Candidate save = candRepo.save(candidate);
				logger.info("Save Candidate" + save);
				// System.out.println(save);
				return new ResponseEntity<>("Candend_time updated successfully", HttpStatus.OK);
			} else {
				logger.info("Candidate not found Error");
				return new ResponseEntity<>("Candidate not found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("UpdateCandEndTime Error" + e.getMessage());
			return new ResponseEntity<>("Error updating candend_time", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "calculateResult")
	public void calculateResult() {
		Candidate c = (Candidate) httpSession.getAttribute("sessionData1");
		List<Answer> findByCandidate = answerService.findByCandidate(c.getCandid());
		List<Integer> idsToUse = new ArrayList<Integer>(c.getCandid());
		Integer marks = 0;
		for (Answer a : findByCandidate) {
			idsToUse.add(a.getQuestion());
		}
		HashSet<Integer> uniqueIds = new HashSet<>(idsToUse);

		for (Integer u : uniqueIds) {
			Integer tempmarks = 0;
			for (Answer a : findByCandidate) {
				if (u == a.getQuestion()) {
					tempmarks += a.getMark();
				}
			}

			if (tempmarks % 2 == 0)

				marks += tempmarks;
			else
				--tempmarks;
		}
		c.setMarkAppear(marks);
		candService.saveCandidate(c);
		logger.info("Total marks secured -> " + marks);
	}
	
	/*
	 * @Transactional
	 * 
	 * @PostMapping(value = "/updateCandStartTime") public ResponseEntity<String>
	 * updateCandStartTime(@RequestBody MyDto dto) {
	 * System.out.println("innnnnn "+dto.getCandStarTime()); try {
	 * //entityManager.getTransaction().begin(); Candidate candidate =
	 * candService.findDetailsById(dto.getCandid()); if (candidate == null) { return
	 * ResponseEntity.badRequest().body("Candidate not found"); } else {
	 * 
	 * 
	 * LocalTime customStartTime = dto.getCandStarTime(); //LocalTime
	 * customStartTime = LocalTime.now(); Integer candid = dto.getCandid();
	 * System.out.println("Candidate " +candidate); candidate = new
	 * Candidate(candidate, customStartTime);
	 * System.out.println("in2 "+candidate.getCandStartTime() );
	 * System.out.println("in3 "+candidate.getCandid()); if
	 * (entityManager.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(
	 * candidate)) {
	 * 
	 * System.out.println("Entity is loaded"); } if
	 * (entityManager.contains(candidate)) {
	 * 
	 * System.out.println("Entity is attached"); } else { candidate =
	 * entityManager.merge(candidate); System.out.println("Entity is detached"); }if
	 * (entityManager.contains(candidate)) {
	 * 
	 * System.out.println("Entity is attached22"); }
	 * 
	 * Candidate c=candService.saveCandidate(candidate); System.out.println("C "+c);
	 * 
	 * 
	 * 
	 * 
	 * entityManager.flush(); //entityManager.getTransaction().commit(); return
	 * ResponseEntity.ok("Updated successfully1111"); } } catch
	 * (EntityNotFoundException e) {
	 * 
	 * return ResponseEntity.badRequest().body("Candidate not found"); }catch
	 * (Exception e) { System.out.println("rrrrr"); if
	 * (entityManager.getTransaction().isActive()) {
	 * entityManager.getTransaction().commit(); } e.printStackTrace(); return new
	 * ResponseEntity<>("Error updating candidate start time",
	 * HttpStatus.INTERNAL_SERVER_ERROR); } }
	 */

}

//-------------------------------------------
