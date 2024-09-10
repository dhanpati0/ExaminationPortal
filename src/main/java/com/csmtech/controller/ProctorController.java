package com.csmtech.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.csmtech.bean.QuestionBean;
import com.csmtech.model.Answer;
import com.csmtech.model.Candidate;
import com.csmtech.model.Configure;
import com.csmtech.model.CorrectAnswer;
import com.csmtech.model.Items;
import com.csmtech.model.Question;
import com.csmtech.model.Reason;
import com.csmtech.model.SubTest;
import com.csmtech.model.SubTestTaker;
import com.csmtech.model.User;
import com.csmtech.repository.CandidateRepository;
import com.csmtech.service.AnswerService;
import com.csmtech.service.CandidateService;
import com.csmtech.service.ConfigureService;
import com.csmtech.service.CorrectAnswerService;
import com.csmtech.service.ItemService;
import com.csmtech.service.QuestionService;
import com.csmtech.service.QuestionSubTestService;
import com.csmtech.service.ReasonService;
import com.csmtech.service.SubTestTakerService;
import com.csmtech.service.UserService;

@Controller
@EnableScheduling
@RequestMapping("exam")
public class ProctorController {

	@Autowired
	private CandidateRepository canRepository;

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	CandidateService candidateService;

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private UserService userService;

	@Autowired
	private QuestionService questionService;

	

	@Autowired
	private ItemService itemService;

	@Autowired
	private ConfigureService configureService;

	@Autowired
	private AnswerService answerService;

	@Autowired
	private ReasonService reasonService;

	@Autowired
	private SubTestTakerService subTestTakerService;
	
	private Integer sharedSubtesttakerId;
	
	@Autowired
	private QuestionSubTestService questionSubTestService;
	
	@Autowired
	private CorrectAnswerService correctAnswerService;

	private static final Logger logger = LoggerFactory.getLogger(ProctorController.class);

	@GetMapping("/forgetPassword")
	public String forgetPassword(Model model) {

		User user = (User) this.httpSession.getAttribute("sessionData");
		model.addAttribute("username", user.getName());

		// System.out.println("get............");
		logger.info("get");
		return "proctor/resetPassword";

	}

	@PostMapping("/changepassword")
	public String changePassword(@RequestParam("newpassword") String newPassword,
			@RequestParam("cpassword") String cpassword, Model model,
			@RequestParam(value = "userid", required = false) Integer userid) {

		User user = (User) this.httpSession.getAttribute("sessionData");
		model.addAttribute("username", user.getName());
		model.addAttribute("userid", user.getUserId());

		// User userchnage = new User();
		if (newPassword.equals(cpassword)) {
			if (userid != null)
				user.setPassword(cpassword);
			userService.saveDetailsOfUser(user);

		} else {
			model.addAttribute("msg", "Entered password is wrong..!! ");
			System.out.println("error");
		}

		return "proctor/resetPassword";
	}

	@GetMapping("/manageProfile")
	public String getmanageProfile(Model model) {
		User user = (User) this.httpSession.getAttribute("sessionData");
		model.addAttribute("username", user.getName());
		return "proctor/manageProfile";
	}

	@GetMapping("/getQuestion")
	public String getQuestion(Model model) {
		
		System.out.println(itemService.findAllItem());
		model.addAttribute("allListItem", itemService.findAllItem());
		return "proctor/addQuestion";
	}

	@PostMapping("/questionAdd")
	public String addQuestion( @RequestParam("itemName") Items itemId,
			@RequestParam("questionText") String questText, @RequestParam("questionType") String questType,
			Model model) {
		Question qs = new Question();

		qs.setQuestionText(questText);
		// qs.setQuestionType(questType);
//		qs.setExam(examId);
		// qs.setItem(itemId);
		qs.setQuestionStatus("0");
//		qs.setExamCode(examId.getExamCode());
		System.out.println(qs + "SDFGHJKI");
		questionService.saveQuestion(qs);
		System.out.println("inside save qst......");
		return "redirect:/exam/getQuestion";
	}

	@PostMapping("/findProctor")
	public ResponseEntity<User> findProctor(@RequestParam("uId") Integer pId) {
		logger.info("=========" + pId);
		// System.out.println("=========" + pId);
		User user = userService.findUserDetailsById(pId);
		return ResponseEntity.ok(user);
	}

	@PostMapping("/examlist")
	public ResponseEntity<List<SubTestTaker>> getAllSubTestTaker() {
		logger.info("get the all subtesttaker");
		List<SubTestTaker> subtesttaker = subTestTakerService.getAllSubTestTaker();
		return ResponseEntity.ok(subtesttaker);
	}

	@PostMapping("/findCandidateList")
	public ResponseEntity<List<Candidate>> candidateList(@RequestParam("sId") Integer subTestTakerId) {
		List<Candidate> candidateList = candidateService.findAllCandidates(subTestTakerId);

		return ResponseEntity.ok(candidateList);
	}

	@PostMapping("/findCandidateListByStatus")
	public ResponseEntity<List<Candidate>> candidateListBySession(@RequestParam("sId") Integer subTestTakerId) {
		logger.info("subtestTakerId" + subTestTakerId);
		List<Candidate> candidateList = candidateService.findAllCandidateByStatusAndSubTestTaker(subTestTakerId);
		return ResponseEntity.ok(candidateList);
	}

	@PostMapping("/extraTime")
	public ResponseEntity<String> extraTime(@RequestParam("candId") String candId,
			@RequestParam("extraTime") String extraTime) {

		Candidate candid = candidateService.findDetailsById(Integer.parseInt(candId));
		LocalTime t = candid.getCandEndTime();
		LocalTime localtime = t.plusMinutes(Long.parseLong(extraTime));
		candid.setCandEndTime(localtime);
		Candidate can = candidateService.saveCandidate(candid);
		String s = (can == null) ? "Failed" : "Success";

		return ResponseEntity.ok(s);
	}

	/**
	 * Our Changes in this file starts from here.
	 * 
	 */

	@GetMapping("/getListOfExams")
	public String getListOfExams(Model model) {
	    User user = (User) this.httpSession.getAttribute("sessionData");
	    //model.addAttribute("username", user.getName());
	    List<SubTestTaker> examList = subTestTakerService.getAllSubTestTaker();
	    logger.info("Returning list of exams....");
	    // System.out.println("Returning list of exams....");

	    List<Configure> examTimeList = new ArrayList<>();

	    for (SubTestTaker sb : examList) {
	        Configure examTime = configureService.getAllConfDetails(sb.getSubTestTakerId());
	        if (examTime != null) {
	            examTimeList.add(examTime);
	        }
	    }

	    Collections.sort(examTimeList, new Comparator<Configure>() {
	        @Override
	        public int compare(Configure c1, Configure c2) {
	            return c2.getTestDate().compareTo(c1.getTestDate());
	        }
	    });

	    //model.addAttribute("examList", examList);
	    model.addAttribute("examTimeList", examTimeList);
	    return "proctor/proctorDashboard";
	}
	
	

	@GetMapping("/monitor")
	public String fetchAllCands(Model model,@RequestParam("subtesttakerId") Integer sId) {
		User user = (User) this.httpSession.getAttribute("sessionData");
		//model.addAttribute("username", user.getName());
		httpSession.setAttribute("subtesttakerId", sId);
		sharedSubtesttakerId = sId;
		
		List<Candidate> candidates = candidateService.findCandidateBySubTestTakerId(sId);
		
		List<Candidate> sortedCandidates = candidates.stream().sorted((c1, c2) -> {
			if (c1.getStatus().equals("inactive") && c2.getStatus().equals("1")) {
				return 1;
			} else if (c1.getStatus().equals("1") && c2.getStatus().equals("inactive")) {
				return -1;
			} else {
				return 0;
			}
		}).collect(Collectors.toList());
		
		List<Configure> configureList= new ArrayList<>();
		for(Candidate c:candidates) {
			Configure examtimeDetails = configureService.getAllConfDetails(c.getSubTestTaker().getSubTestTakerId());
			configureList.add(examtimeDetails);
			break;
		}
		if (candidates == null || configureList == null) {
	       
	        return "error";
	    }
		model.addAttribute("configList", configureList);
		model.addAttribute("candidates", sortedCandidates);
		return "proctor/monitor";
	}

	@GetMapping("/updateStatus")
    public String updateCandidateStatus(@RequestParam("id") String id) {
		
		Integer sId = (Integer) httpSession.getAttribute("subtesttakerId");
		Integer candid=null;
		try {
			candid=new Integer(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		boolean flag = candidateService.updateStatus(candid);
		if (flag) {
			//System.out.println("Updated Successfully.");
		}
        return "redirect:/exam/monitor?subtesttakerId=" + sId;
    }

	
	
	/*
	 * @GetMapping("/updateStatus") public String
	 * updateCandidateStatus(@RequestParam("id") String id) {
	 * 
	 * System.out.println(id +"//////////"); try { Integer candid =
	 * Integer.parseInt(id); boolean flag = candidateService.updateStatus(candid);
	 * 
	 * if (flag) { System.out.println("Updated Successfully."); } else {
	 * System.out.println("Update failed."); }
	 * 
	 * } catch (NumberFormatException e) { System.out.println("Invalid ID format: "
	 * + id); } catch (Exception e) { // Log the exception e.printStackTrace(); }
	 * 
	 * return "redirect:/exam/monitor"; }
	 */

	 @Scheduled(fixedRate = 5000)
	    public void sendDataViaWebSocket() {
	        try {
	            List<Candidate> candidates = candidateService.findCandidateBySubTestTakerId(sharedSubtesttakerId);
	            List<Reason> reasons = reasonService.findAll();
	            List<Candidate> sortedCandidates = candidates.stream().sorted((c1, c2) -> {
	                if (c1.getStatus().equals("inactive") && c2.getStatus().equals("1")) {
	                    return 1;
	                } else if (c1.getStatus().equals("1") && c2.getStatus().equals("inactive")) {
	                    return -1;
	                } else {
	                    return 0;
	                }
	            }).collect(Collectors.toList());

	            List<Configure> configureList = new ArrayList<>();
	            for (Candidate c : candidates) {
	                Configure examtimeDetails = configureService.getAllConfDetails(c.getSubTestTaker().getSubTestTakerId());
	                configureList.add(examtimeDetails);
	                break;
	            }

	            Map<String, Object> combinedData = new HashMap<>();
	            combinedData.put("reasons", reasons);
	            combinedData.put("candidates", sortedCandidates);
	            combinedData.put("configurations", configureList);
	            template.convertAndSend("/topic/combinedData", combinedData);
	        } catch (MessageConversionException e) {
	            // Handle message conversion exception
	            e.printStackTrace(); // Or log the error
	        } catch (Exception e) {
	            // Handle other exceptions
	            e.printStackTrace(); // Or log the error
	        }
	    }

	

	 @GetMapping("candidateAnswerSheet")
		public String getcandidateAnswerSheet(Model model) {
		 //User user = (User) this.httpSession.getAttribute("sessionData");
			//model.addAttribute("username", user.getName());
			System.out.println("findAnswerSheet");
			return "proctor/answerSheet";
		}

		@PostMapping("getCandidateAnswerSheet")
		public String findCandidateAnswerSheet(@RequestParam("candidateEmail") String candidateEmail,RedirectAttributes rd,HttpServletRequest req,Model model) {
			//User user = (User) this.httpSession.getAttribute("sessionData");
			//model.addAttribute("username", user.getName());
			Candidate c = candidateService.findCandidateByCandidateEmail(candidateEmail);
			System.out.println("/"+c);
			if(c!=null) {
						
			Integer cId = c.getCandid();
			Configure config = configureService.findConfigureBySubTestTakerId(c.getSubTestTaker().getSubTestTakerId());
			model.addAttribute("candDetails", c);
			model.addAttribute("configDetails", config);
			if(config!=null) {
			Integer enterNoQuestion = config.getEnterNoQuestion();
			SubTest subtest = config.getSubTest();
			
			List<QuestionBean> questionforCandidates = questionSubTestService.findQuestionsForIndividualCandidate(subtest.getSubTestId(),cId,enterNoQuestion);
			System.out.println("}}}}}}"+questionforCandidates);
			
			httpSession.setAttribute("questCand", questionforCandidates);
			
				
			if(questionforCandidates.isEmpty()) {
				rd.addFlashAttribute("notAvialable", "emptyQuest");
				return "redirect:./candidateAnswerSheet";
			}
			// List<Answer> answers = answerService.findAnswerByCandidateId(cId);
			model.addAttribute("candQuestions", questionforCandidates);
			
			List<Answer> answerGivenBycandidate = answerService.getAnswersByCandidateId(cId);
			model.addAttribute("answerGivenBycandidate", answerGivenBycandidate);
			System.out.println("]]]]"+answerGivenBycandidate);
			httpSession.setAttribute("answerGivenBycandidate", answerGivenBycandidate);
			
			
			List<CorrectAnswer> allAnswer = correctAnswerService.getAllCorrectAnswer();
			httpSession.setAttribute("allAnswer", allAnswer);
			return "proctor/candidateAnswersheet";
			}else {
				rd.addFlashAttribute("notConfigure", "failed");
				return "redirect:./candidateAnswerSheet";
			}
			}else {
				rd.addFlashAttribute("noCandidate", "noCand");
				return "redirect:./candidateAnswerSheet";

			}
			
		}

	@PostMapping("/updateCandidate")
	public void updateCandidate(Model model, @RequestParam("cId") String cId, @RequestParam("reason") String reason,
			HttpServletResponse response) throws IOException {
		Integer Id = null;
		String numericPart = cId.replaceAll("[^0-9]", "");
		Id = Integer.parseInt(numericPart);
		Candidate campu = candidateService.findDetailsById(Id);
		// System.out.println(cId);
		Reason existingCandidate = reasonService.findByCandidateId(Id);
		System.out.println(existingCandidate);
		if (existingCandidate != null) // is candidate is already present in reason table
		{
			if (existingCandidate.getReason().equalsIgnoreCase("verified")) {
				Reason res = new Reason();
				existingCandidate.setDate(LocalDate.now());
				existingCandidate.setTime(LocalTime.now());
				existingCandidate.setReason(reason);
				Reason setReason = reasonService.saveReason(existingCandidate);
				Candidate blockedCandidate = candidateService.updateCandidateById(Id);
				blockedCandidate.setProgress("Blocked");
				blockedCandidate.setStatus("inactive");
				blockedCandidate.setPause("Yes");
				Candidate updatedCandidate = candidateService.saveCandidate(blockedCandidate);
			} else {
				existingCandidate.setReason("Verified");
				existingCandidate.setDate(LocalDate.now());
				existingCandidate.setTime(LocalTime.now());
				Reason setReason = reasonService.saveReason(existingCandidate);
				Candidate blockedCandidate = candidateService.updateCandidateById(Id);
				blockedCandidate.setProgress("Logout");
				blockedCandidate.setResultStatus("");
				blockedCandidate.setStatus("inactive");
				blockedCandidate.setPause("No");
				Candidate updatedCandidate = candidateService.saveCandidate(blockedCandidate);
			}
			// Reason res = new Reason();
		} else {
			Reason res = new Reason();
			res.setDate(LocalDate.now());
			res.setTime(LocalTime.now());
			res.setCandidateId(Id);
			res.setReason(reason);
			Reason setReason = reasonService.saveReason(res);
			Candidate blockedCandidate = candidateService.updateCandidateById(Id);
			blockedCandidate.setProgress("Blocked");
			blockedCandidate.setStatus("1");
			blockedCandidate.setPause("Yes");
			Candidate updatedCandidate = candidateService.saveCandidate(blockedCandidate);
			
		}
		response.getWriter().write(campu.getSubTestTaker().getSubTestTakerId() + "");
	}
	
	@RequestMapping("reasonPopupForBlock")
	public String getReasonPopUpForBlock(@RequestParam("cId") String cId) {
		String numericPart = cId.replaceAll("[^0-9]", "");
		Integer sId = (Integer) httpSession.getAttribute("subtesttakerId");
		System.out.println("////"+sId);
		Integer Id = Integer.parseInt(numericPart);
		System.out.println("/////////////"+Id);
		httpSession.setAttribute("BlockedCandidateId", Id);
		return "proctor/blockPopUp";
	}
	
	@GetMapping("backToAnswersheet")
	public String backToAnswersheet() {
		
		return "redirect:./candidateAnswerSheet";
	}
}
