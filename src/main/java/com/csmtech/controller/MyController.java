package com.csmtech.controller;

import com.csmtech.exporter.ResultExcelExporter;
import com.csmtech.model.*;
import com.csmtech.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;

@Controller
@CrossOrigin("http://localhost:8090/")
@RequestMapping("/exam")
public class MyController {

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private CandidateService candService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private ConfigureService configureService;

	@Autowired
	private TestTakerService testTakerService;

	@Autowired
	private SubTestTakerService subTestTakerService;
	@Autowired
	private ReasonService reasonServices;
	@Autowired
	private CandidateService candidateService;

	@Autowired
	private CandidateController candidateController;

	private static final Logger logger = LoggerFactory.getLogger(MyController.class);

	@GetMapping("/login")
	public String getLoginPage(Model model) {
		logger.info("getting......");
		return "pageLogin";
	}

	@RequestMapping("/error")
	public String handleError() {
		return "error"; // Return the name of your custom error page (error.html)
	}

	@GetMapping("/backLogin")
	public String backtologin() {
		return "redirect:./login";
	}

	@GetMapping("/logout")
	public String getLogout(HttpServletResponse response) {
// Clear session attributes
		logger.info("------------logout with some inactivity or other error ====>>");
		Candidate cand = (Candidate) httpSession.getAttribute("sessionData1");
		cand.setProgress("Logout");
		Reason existingCand = reasonServices.findByCandidateId(cand.getCandid());
		if ((existingCand != null) && existingCand.getReason() != "Verified") {
			cand.setPause("yes");
		}
		LocalTime time = LocalTime.now();
		cand.setCandEndTime(time);
		System.out.println("Candidate " + cand);
		candService.saveCandidate(cand);
		httpSession.removeAttribute("sessionData");
		httpSession.removeAttribute("savesubItem");
		logger.info("----------user session cleared-----------");

// Prevent caching
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setHeader("Expires", "0"); // Proxies

		return "redirect:./login?logout=true"; // Add cache-busting parameter
	}

	@GetMapping("/logout1")
	public String getLogoutForCompletion(HttpServletResponse response) {
		logger.info("------------logout1 Completed ====>>");
		Candidate cand = (Candidate) httpSession.getAttribute("sessionData1");
		cand.setProgress("Completed");
		LocalTime time = LocalTime.now();
		cand.setCandEndTime(time);
		candService.saveCandidate(cand);
// Clear session attributes
		httpSession.removeAttribute("sessionData1");
		httpSession.removeAttribute("sessionData");
		httpSession.removeAttribute("savesubItem");
		logger.info("----------logout1 user session cleared-----------");
// Prevent caching
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setHeader("Expires", "0"); // Proxies

		return "redirect:./login?logout=true"; // Add cache-busting parameter
	}

	@GetMapping("/blockLogout")
	public String getBlockLogoutForCompletion(HttpServletResponse response) {
		logger.info("------------Blocked Completed ====>>");
		Integer candId = (Integer) httpSession.getAttribute("BlockedCandidateId");
		Candidate cand = candidateService.findDetailsById(candId);
//cand.setProgress("Completed");
		LocalTime time = LocalTime.now();
		cand.setCandEndTime(time);
//cand.setPause("Yes");
		candService.saveCandidate(cand);
// Clear session attributes
		httpSession.removeAttribute("sessionData1");
		httpSession.removeAttribute("sessionData");
		httpSession.removeAttribute("savesubItem");
		logger.info("----------logout1 user session cleared-----------");
// Prevent caching
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setHeader("Expires", "0"); // Proxies

		return "redirect:./login?logout=true"; // Add cache-busting parameter
	}

	@PostMapping("/loginHere")

	public String loginAdminHere(@RequestParam String username, @RequestParam String password, Model model,

			RedirectAttributes rd, HttpServletRequest req) throws ParseException {
		System.out.println(username + "///////////");
// RedirectAttributes rd) 
		if (password.isBlank() && username.isBlank()) {
			System.out.println("here come");
			rd.addFlashAttribute("Blank", "isBlank");
			return "redirect:./login";
		} else {

			boolean flag = true;

			logger.info("------------Login ====>>" + username);

			logger.info(password);

// String decodedPassword = decodeText(password);

			User user = userService.findUserByUsernameAndPasswordForCheck(username, password);
			flag = userService.findUserByUsernameAndPassword(username, password);

			if (flag) {

				int roleid = userService.findRoleIdByUsernameAndPassword(username, password);

				Role role = roleService.findById(roleid);

				logger.info("---------Role Id is -> " + roleid);

				String roleName = role.getRoleName();

				httpSession.setAttribute("sessionData", user);

				if ("Admin".equals(username) && user.getPassword().equals(password)) {

					logger.info("--------roleName->   " + username);

					rd.addFlashAttribute("alert", "success");

					return "redirect:./adminDashboard";

				} else if ("Proctor".equals(username) && user.getPassword().equals(password)) {
					model.addAttribute("username", user.getName());

//return "redirect:http://localhost:8090/web?user="+user.getUserId();
//return "proctor/proctorDashboard";
					return "redirect:./getListOfExams";

				} else if ("Hr".equals(username) && user.getPassword().equals(password)) {

					model.addAttribute("username", user.getName());

					return "hr/hrDashboard";

				}

				else {
					model.addAttribute("invalid", "login failed");
				}

			}

			else {

				Candidate c = candService.getCandidateByEmail(username);
				if (c == null) {
					rd.addFlashAttribute("NotFound", "isFound");
					return "redirect:./login";
				}
				String pass = CandidateController.decodeText(c.getCandpassword());

				logger.info("decode password" + pass);

				String candPassword = c.getCandpassword();

				Candidate cand = candService.findCandidateByCandnameAndPassword(username, candPassword);
				req.getSession().setAttribute("candId", cand.getCandid());
				logger.info("Candidate ID :: " + cand.getCandid());
				logger.info("INSIDE ELSE");

				logger.info("____--------------" + cand);

				logger.info("first password" + password);

				if (pass.equals(password)) {

					logger.info("password matched successfully");

					httpSession.setAttribute("sessionData1", cand);
					Integer sId = cand.getSubTestTaker().getSubTestTakerId();
					System.out.println(sId + "/////////");
					Configure config = configureService.findConfigureBySubTestTakerId(sId);
					System.out.println("configure " + config);
//author :megha kumari
					
					
					ZoneId zoneId = ZoneId.of("Asia/Kolkata");
					LocalDate currentDate = LocalDate.now(zoneId);
					System.out.println(currentDate+"curr Date");
					LocalTime currentTime = LocalTime.now(zoneId);
					
					
					//long currentTime1 = currentTime.toNanoOfDay() / 1_000_000;

					System.out.println(currentTime+"curr Time");
					/*
					 * LocalTime examL = config.getLoginTime(); long examLogin = examL.toNanoOfDay()
					 * / 1_000_000; System.out.println(config.getLoginTime());
					 * System.out.println(examLogin+"exam Time"); LocalTime startE =
					 * config.getStartTime(); //System.out.println(startExam+"start Time"); long
					 * startExam = startE.toNanoOfDay() / 1_000_000; LocalTime endE =
					 * config.getEndTime(); long endExam = endE.toNanoOfDay() / 1_000_000;
					 */
				
					LocalDateTime t = LocalDateTime.of(config.getTestDate(), currentTime);
					Long time =t.atZone(zoneId).toInstant().toEpochMilli();
					LocalDateTime of = LocalDateTime.of(config.getTestDate(), config.getLoginTime());
					 long examLogin = of.atZone(zoneId).toInstant().toEpochMilli();
					LocalDateTime of1 = LocalDateTime.of(config.getTestDate(), config.getStartTime());
					long startExam = of1.atZone(zoneId).toInstant().toEpochMilli();
					LocalDateTime of2 = LocalDateTime.of(config.getTestDate(), config.getEndTime());
					long endExam = of2.atZone(zoneId).toInstant().toEpochMilli();
					//long currentTime = System.currentTimeMillis();
					
					
					//System.out.println(currentTime1 + "//" + config.getLoginTime() + "//" + of);
					if (cand != null) {
						logger.info("Candidate Status :: " + cand.getStatus());
						if ("1".equals(cand.getStatus()) && "Completed".equals(cand.getProgress())) {
							logger.info("Status = Active");
							rd.addFlashAttribute("cannotLogin", "login");
							return "redirect:./login";
						} else if (currentTime.isBefore(config.getLoginTime())) {
							rd.addFlashAttribute("TimeNot", "NotStarted");
							return "redirect:./login";
						}else if(currentDate.isBefore(currentDate)&&currentDate.isAfter(currentDate)) {
							rd.addFlashAttribute("DateNot", "NotDateToday");
							return "redirect:./login";
						}
						else if ("1".equals(cand.getStatus()) && "Logout".equals(cand.getProgress())) {
							rd.addFlashAttribute("Progress1", "AutoLogout");
							return "redirect:./login";
						} else if ("1".equals(cand.getStatus()) && "Blocked".equals(cand.getProgress())) {
							rd.addFlashAttribute("Progress2", "BlockedCandidate");
							return "redirect:./login";
						} else {
							HashMap<String, Long> mp = new HashMap<>();

							mp.put("examLogin", examLogin);
							mp.put("startExam", startExam);
							mp.put("endExam", endExam);
							mp.put("currentTime", time);
							 
							
							
							model.addAllAttributes(mp);
//Store Candidate Login Time :: Gopi 27-10-23//
							Locale clientLocale = req.getLocale();
							Calendar calendar = Calendar.getInstance(clientLocale);
							TimeZone clientTimeZone = calendar.getTimeZone();
							cand.setCandLoginTime(LocalTime.now(clientTimeZone.toZoneId()));
							candService.saveCandidate(cand);
//Storing client browser time done//
//Active candidate after login
							cand.setStatus("1");
							candService.updateCandidate(cand);
							return "candidate/candidateDashboard";

						}

					}
				}
			}
			rd.addFlashAttribute("alert", "failed");
			return "redirect:./login";
		}
	}

	@GetMapping("/adminDashboard")

	public String adminDashboard(Model model, RedirectAttributes rd) {

		User user = (User) this.httpSession.getAttribute("sessionData");

		logger.info(" here inside admindashboard");

		if (user == null) {
			rd.addFlashAttribute("alert", "failed");

			return "redirect:./login";

		}

		else

			model.addAttribute("username", user.getName());

		return "admin/adminDashboard";

	}

	@GetMapping("/addCandidate")
	public String addCandPage(Model model) {

		User user = (User) this.httpSession.getAttribute("sessionData");
		model.addAttribute("username", user.getName());
		model.addAttribute("allCandidate", candService.findAllCandidate());

		return "admin/addCandidate";

	}

	@GetMapping("/deleteCandidate")
	public String deleteCandidateById(@RequestParam("candid") Integer candid) {

		candService.deleteCandidateById(candid);
		return "redirect:./addCandidate";
	}

	@GetMapping("/updateCandidate")
	public String updateStudentForm(Model model, @RequestParam("candid") Integer candid) {
		logger.info(candid.toString());
		logger.info(candService.updateCandidateById(candid).toString());
		model.addAttribute("cand", candService.updateCandidateById(candid));

		return "forward:/exam/addCandidate";
	}

	@GetMapping("/manageUsers")
	public String manageUser(Model model) {
		List<Role> role = roleService.findAllRole();
		model.addAttribute("roleList", role);
		User user = (User) this.httpSession.getAttribute("sessionData");
		model.addAttribute("username", user.getName());
		model.addAttribute("allUser", userService.getAllUser());

		return "admin/userManage";

	}

	@PostMapping("getUserById")
	@ResponseBody
	public User getUser(Integer userId) {
		logger.info("getting+++++++++++++++" + userService.findUserDetailsById(userId));
		return userService.findUserDetailsById(userId);
	}

	@PostMapping("/saveUserDetails")
	public String addUser(@RequestParam(value = "userId", required = false) Integer userId,
			@RequestParam("name") String name, @RequestParam("username") String userName,
			@RequestParam("email") String email, @RequestParam("mobileNo") String mobileNo,
			@RequestParam("gender") String gender, @RequestParam("userAddress") String address,
			@RequestParam("role") Role userRole, Model model, RedirectAttributes rd) throws Exception {
		logger.info("&&&&&&&&");

		boolean userExist = userService.getAllUser().stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(userName));
		User user = new User();
		user.setName(name);
		user.setUsername(userName);
		user.setPassword(userName + "@#");
		user.setEmail(email);
		user.setMobileNo(mobileNo);
		user.setGender(gender);
		user.setUserAddress(address);
		user.setRole(userRole);
		user.setIsDelete("No");
		user.setStatus("0");
		
		
		if (userExist==false && userId==null) {
			rd.addFlashAttribute("exist", userName);
			userService.saveDetailsOfUser(user);
			logger.info("+++++++++++++++++++++" + user);
			rd.addFlashAttribute("userAdded", userName);
		}else if(userExist==false || userId!=null) {
			logger.info("2ND CASE");
			user.setUserId(userId);
			userService.saveDetailsOfUser(user);
			rd.addFlashAttribute("UpdateUser", "User Details Update successfully");
		}
		else {
			Integer uId = userService.getIdByName(userName);
			User u = userService.findUserDetailsById(userId);
			if (uId != null && u.getIsDelete().equalsIgnoreCase("Yes")) {
				//System.out.println("STATUS::" + t.getIsDeleted());
				logger.info("STATUS::" + u.getIsDelete());
				u.setUserId(uId);
				u.setIsDelete("No");
				userService.saveDetailsOfUser(u);
				logger.info("ID NULLL BUT EXIST NOT 1ST CASE");
				//System.out.println("ID NULLL BUT EXIST NOT 1ST CASE");
				rd.addFlashAttribute("UpdateUser", "User Details Update successfully");
			} else {
				//System.out.println("ID NULL");
				logger.error("ID NULL");
				logger.info("ID NULL: College Already Exist");
				rd.addFlashAttribute("exist", "User Already Exist");
			}
		}
		return "redirect:./manageUsers";

	}

	@GetMapping("/deleteUser")
	public String deleteUserById(@RequestParam("userId") Integer userId, RedirectAttributes rd) {
		userService.deleteUserById(userId);
		rd.addFlashAttribute("delete", "delete");
		return "redirect:./manageUsers";
	}

	@RequestMapping(path = "/updateUser")
	public String updateUser(@RequestParam(name = "userId") Integer userId, RedirectAttributes rd, Model model) {
		User us = userService.findUserDetailsById(userId);
		rd.addFlashAttribute("us", us);
// model.addAttribute("us", us);
		logger.info("!!!!!!!!!!!11" + us);
		return "redirect:./manageUsers";
	}

	@GetMapping(path = "/startExam")
	public String startExam() {
		logger.info("yes....");
		return "candidate/questions";
	}

	@GetMapping("/getSubById")

	public void searchTest(@RequestParam(name = "testTakerId") Integer testTakerId, HttpServletResponse resp)

			throws IOException {

		Integer sub = (Integer) httpSession.getAttribute("subtesttakr");

		logger.info("&&&" + testTakerId);

		logger.info("NM***" + subTestTakerService.getAllSubTestTakerByTestTakerId(testTakerId));

		List<SubTestTaker> sm = subTestTakerService.getAllSubTestTakerByTestTakerId(testTakerId);

		logger.info("@@@@@" + sm);

		String filterAjaxValue = "<option value='0'>--select--</option>";

		for (SubTestTaker c : sm)

			if (c.getSubTestTakerId() == sub)

				filterAjaxValue += "<option value='" + c.getSubTestTakerId() + "' selected = 'selected' >"

						+ c.getSubTestTakerName() + "</option>";

			else

				filterAjaxValue += "<option value=" + c.getSubTestTakerId() + ">" + c.getSubTestTakerName()

						+ "</option>";

		logger.info("inside controller" + filterAjaxValue);

		resp.getWriter().print(filterAjaxValue);

	}

	@PostMapping("/searchTest")
	public String searchTest(@RequestParam(name = "testTakerName") Integer testTakerId,
			@RequestParam(name = "subTestTakerName") Integer subTetTakerId, Model model, RedirectAttributes rd) {
		logger.info("test__" + testTakerId + "::" + "subTest___" + subTetTakerId);

		model.addAttribute("testTakerList", testTakerService.getAll());
		httpSession.setAttribute("subtesttakr", subTetTakerId);
		httpSession.setAttribute("testtaker", testTakerId);

		if (testTakerId == 0 && subTetTakerId == 0) {
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" + testTakerId);
			model.addAttribute("resultList", candService.findAllCandidate());
		} else if (testTakerId != 0 && subTetTakerId == 0) {
			logger.info("WITH PROID:::" + testTakerId);
			model.addAttribute("resultList", candService.findCandidateByTestTakerId(testTakerId));
// logger.info("BYTEST---" + candService.findCandidateByTestTakerId(testTakerId));
		} else if (testTakerId != 0 && subTetTakerId != 0) {
			System.out.println(candService.findBytestTakerIdAndsubTetTakerId(testTakerId, subTetTakerId) + "////");
			model.addAttribute("resultList", candService.findBytestTakerIdAndsubTetTakerId(testTakerId, subTetTakerId));
		}
		return "admin/result";
	}

	@RequestMapping("/result")
	public String viewResult(Model model, RedirectAttributes rd) {
		httpSession.setAttribute("subtesttakr", 0);
		httpSession.setAttribute("testtaker", 0);
		model.addAttribute("resultList", candService.findAllCandidate());
		model.addAttribute("testTakerList", testTakerService.getAll());

		return "admin/result";
	}

	@GetMapping("/export/Result")
	public void exportResult(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Result" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);
		Integer testTakerId = (Integer) httpSession.getAttribute("testtaker");
		Integer subtestTakerId = (Integer) httpSession.getAttribute("subtesttakr");
		List<Candidate> listCandidates = candService.getBytestTakerIdAndsubTetTakerId(testTakerId, subtestTakerId);

		ResultExcelExporter excelExporter = new ResultExcelExporter(listCandidates);

		excelExporter.export(response);
	}

	@GetMapping("/requestLogin")
	public String getRequestFromCandidateWhoLogedOut(@RequestParam(name = "username") String candidateemail,
			Model model, RedirectAttributes rd) {
		System.out.println("Hello in " + candidateemail);
		Candidate cand = candService.findCandidateByCandidateEmail(candidateemail);
		if (cand == null) {
			rd.addFlashAttribute("requestFailed", "emailFailed");
			return "redirect:./login";
		} else {
			model.addAttribute("candidate", cand);
			httpSession.setAttribute("candidateSession", cand);
			System.out.println("This is Hell" + cand);
		}
		return "requestLogin";
	}

	@PostMapping("/checkCandidate")
	public void checkCandidate(@RequestParam("username") String username, HttpServletResponse res) throws IOException {
		System.out.println("Username ------------" + username);
		Candidate existingCand = candService.findCandidateByCandidateEmail(username);
//System.out.println(existingCand.getProgress().equalsIgnoreCase("logout"));
		if (existingCand == null) {
			System.out.println("no can found");
		} else {
			if (existingCand.getProgress().equalsIgnoreCase("Logout")
					|| existingCand.getProgress().equalsIgnoreCase("Blocked"))
				res.getWriter().print("true");
			else
				res.getWriter().print("false");
		}
	}

	@PostMapping("/savePopupData")
	public String savePopupData(@RequestParam("reasonForLogout") String reasonForLogout) {
		System.out.println("reason save popup");

// Update the candidate with the new data (you can modify this logic)
// For example, saving the inputValue to a specific field in the Candidate
// entity
		Candidate cand = (Candidate) httpSession.getAttribute("candidateSession");
		Integer cId = cand.getCandid();
		System.out.println();
		if (cand.getReasonForLogOut() != null) {
			candService.updateCandidateIfReasonAvailable(cId, reasonForLogout);
		} else {

// Candidate c = new Candidate();
			cand.setReasonForLogOut(reasonForLogout);
// Save the updated candidate
			candService.saveCandidate(cand);
			System.out.println("save reason");
		}
		ResponseEntity.ok("Reason send successfully!");

		return "redirect:./login";
	}

}