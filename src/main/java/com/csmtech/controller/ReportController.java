package com.csmtech.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.xmlbeans.impl.repackage.Repackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.csmtech.exporter.CandidateExcelExporter;
import com.csmtech.service.CandidateService;
import com.csmtech.service.TestTakerService;

@Controller
@RequestMapping("exam")
public class ReportController {

	@Autowired
	private CandidateService candService;

	@Autowired
	private TestTakerService testTakerService;

	@Autowired
	private HttpSession httpSession;

	private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

	@GetMapping("/analysisReport")
	public String getAnalysisReport(Model model) {

		return "admin/analysisReport";
	}

	@GetMapping("/registerCandidatesReport")
	public String getRegisterCandidatesReport(Model model) {
		System.out.println(candService.findAllCandidate());
		model.addAttribute("allRegisterCandidates", candService.findAllCandidate());
		model.addAttribute("testTakerList", testTakerService.getAllCollege());

		// new code for selected dropdown

		httpSession.setAttribute("subtesttakr", null);
		httpSession.setAttribute("testtaker", null);
		return "admin/registerCandidates";

	}

	@PostMapping("/searchRegisterCandidate")
	public String searchRegisterCandidate(@RequestParam(name = "testTakerName") Integer testTakerId,
			@RequestParam(name = "subTestTakerName") Integer subTetTakerId, Model model, RedirectAttributes rd) {
		logger.info("test__" + testTakerId + "::" + "subTest___" + subTetTakerId);
		model.addAttribute("testTakerList", testTakerService.getAllCollege());
		httpSession.setAttribute("subtesttakr", subTetTakerId);
		httpSession.setAttribute("testtaker", testTakerId);
		if (testTakerId == 0 && subTetTakerId == 0) {
			logger.info("IFF");
			model.addAttribute("allRegisterCandidates", candService.findAllCandidate());
		} else if (testTakerId != 0 && subTetTakerId == 0) {
			logger.info("WITH PROID:::" + testTakerId);
			model.addAttribute("allRegisterCandidates", candService.findCandidateByTestTakerId(testTakerId));
// logger.info("BYTEST---" + candService.findCandidateByTestTakerId(testTakerId));
		} else if (testTakerId != 0 && subTetTakerId != 0) {
			model.addAttribute("allRegisterCandidates",
					candService.findBytestTakerIdAndsubTetTakerId(testTakerId, subTetTakerId));
		}
		return "admin/registerCandidates";
	}

	@GetMapping("/appearedCandidatesReport")
	public String getAppearedCandidatesReport(Model model) {

		System.out.println(candService.findAllCandidateWhoAppearedTheExam());
		model.addAttribute("allAppearedCandidates", candService.findAllCandidateWhoAppearedTheExam());
		model.addAttribute("testTakerList", testTakerService.getAllCollege());
		httpSession.setAttribute("subtesttakr", null);
		httpSession.setAttribute("testtaker", null);
		return "admin/appearedCandidates";

	}

	@PostMapping("/searchAppearedCandidate")
	public String searchAppearedCandidate(@RequestParam(name = "testTakerName") Integer testTakerId,
			@RequestParam(name = "subTestTakerName") Integer subTetTakerId, Model model, RedirectAttributes rd) {
		logger.info("test__" + testTakerId + "::" + "subTest___" + subTetTakerId);
		model.addAttribute("testTakerList", testTakerService.getAllCollege());
		httpSession.setAttribute("subtesttakr", subTetTakerId);
		httpSession.setAttribute("testtaker", testTakerId);
		if (testTakerId == 0 && subTetTakerId == 0) {
			logger.info("IFF");
			model.addAttribute("allAppearedCandidates", candService.findAllCandidateWhoAppearedTheExam());
		} else if (testTakerId != 0 && subTetTakerId == 0) {
			logger.info("WITH PROID:::" + testTakerId);
			model.addAttribute("allAppearedCandidates",
					candService.findAllCandidateWhoAppearedTheExamByTestTakerId(testTakerId));
// logger.info("BYTEST---" + candService.findCandidateByTestTakerId(testTakerId));
		} else if (testTakerId != 0 && subTetTakerId != 0) {
			model.addAttribute("allAppearedCandidates", candService
					.findAllCandidateWhoAppearedTheExamtestTakerIdAndsubTetTakerId(testTakerId, subTetTakerId));
		}
		return "admin/appearedCandidates";
	}

	@GetMapping("/notAppearedCandidatesReport")
	public String getNotAppearedRegisterCandidatesReport(Model model) {
		System.out.println(candService.findAllCandidateWhoNotAppearedTheExam());
		model.addAttribute("allNotAppearedCandidates", candService.findAllCandidateWhoNotAppearedTheExam());
		model.addAttribute("testTakerList", testTakerService.getAllCollege());
		httpSession.setAttribute("subtesttakr", null);
		httpSession.setAttribute("testtaker", null);
		return "admin/notAppearedCandidates";

	}

	@PostMapping("/searchNotAppearedCandidate")
	public String searchNotAppearedCandidate(@RequestParam(name = "testTakerName") Integer testTakerId,

			@RequestParam(name = "subTestTakerName") Integer subTetTakerId, Model model, RedirectAttributes rd) {
		logger.info("test__" + testTakerId + "::" + "subTest___" + subTetTakerId);
		model.addAttribute("testTakerList", testTakerService.getAllCollege());
		httpSession.setAttribute("subtesttakr", subTetTakerId);
		httpSession.setAttribute("testtaker", testTakerId);
		if (testTakerId == 0 && subTetTakerId == 0) {
			logger.info("IFF");
			model.addAttribute("allNotAppearedCandidates", candService.findAllCandidateWhoNotAppearedTheExam());
		} else if (testTakerId != 0 && subTetTakerId == 0) {
			logger.info("WITH PROID:::" + testTakerId);
			model.addAttribute("allNotAppearedCandidates",
					candService.findAllCandidateWhoNotAppearedTheExamByTestTakerId(testTakerId)); //
			logger.info("BYTEST---" + candService.findCandidateByTestTakerId(testTakerId));
		} else if (testTakerId != 0 && subTetTakerId != 0) {
			model.addAttribute("allNotAppearedCandidates", candService
					.findAllCandidateWhoNotAppearedTheExamtestTakerIdAndsubTetTakerId(testTakerId, subTetTakerId));
		}
		return "admin/notAppearedCandidates";
	}

	@GetMapping("/qualifiedCandidatesReport")
	public String getQualifuedCandidatesReport(Model model) {
		model.addAttribute("allQualifiedCandidates", candService.findAllQualifiedCandidates());
		model.addAttribute("testTakerList", testTakerService.getAllCollege());
		httpSession.setAttribute("subtesttakr", null);
		httpSession.setAttribute("testtaker", null);
		return "admin/qualifiedCandidates";

	}

//	@PostMapping("/searchQualifiedCandidateByCutoff")
//	public String searchQualifiedCandidate(@RequestParam(name = "testTakerName") Integer testTakerId,
//			@RequestParam(name = "subTestTakerName") Integer subTetTakerId, @RequestParam("cutoff") Integer cutoffMark,Model model, RedirectAttributes rd) {
//		logger.info("test__" + testTakerId + "::" + "subTest___" + subTetTakerId);
//		System.out.println("we here"+cutoffMark);
//		model.addAttribute("testTakerList", testTakerService.getAll());
//		httpSession.setAttribute("subtesttakr", subTetTakerId);
//		httpSession.setAttribute("testtaker", testTakerId);
//		if (testTakerId == 0 && subTetTakerId == 0 && cutoffMark == 0) {
//			logger.info("IFF");
//			System.out.println("ciff");
//			model.addAttribute("allQualifiedCandidates", candService.findAllQualifiedCandidates());
//		} else if (testTakerId != 0 && subTetTakerId == 0) {
//			logger.info("WITH PROID:::" + testTakerId);
//			model.addAttribute("allQualifiedCandidates", candService.findAllCandidateWhoAppearedTheExamByTestTakerId(testTakerId));
//// logger.info("BYTEST---" + candService.findCandidateByTestTakerId(testTakerId));
//		} else if (testTakerId != 0 && subTetTakerId != 0) {
//			model.addAttribute("allQualifiedCandidates", candService.findAllCandidateWhoAppearedTheExamtestTakerIdAndsubTetTakerId(testTakerId, subTetTakerId));
//		}
//		
//		else if (testTakerId != 0 && subTetTakerId == 0 && cutoffMark != 0) {
//			model.addAttribute("allQualifiedCandidates", candService.findAllCandidateWhoQualifiedByCutoffMark(testTakerId,cutoffMark));
//		}
//		return "admin/qualifiedCandidates";
//	}
//	
	@RequestMapping("/searchQualifiedCandidateByCutoff")
	public String searchQualifiedCandidate(@RequestParam(name = "testTakerName") Integer testTakerId,
			@RequestParam(name = "subTestTakerName") Integer subTetTakerId,
			@RequestParam(name = "cutoff", required = false) Integer cutoffMark, Model model) {
		System.out.println("Hello");
		System.out.println("we here" + cutoffMark);
		model.addAttribute("testTakerList", testTakerService.getAllCollege());
		httpSession.setAttribute("subtesttakr", subTetTakerId);
		httpSession.setAttribute("testtaker", testTakerId);
		if (testTakerId == 0 && subTetTakerId == 0 && cutoffMark == null) {
			logger.info("IFF");
			System.out.println("ciff");
			model.addAttribute("allQualifiedCandidates", candService.findAllQualifiedCandidates());
		}if (testTakerId != 0 && subTetTakerId == 0 && cutoffMark == null) {
			logger.info("WITH PROID:::" + testTakerId);
			model.addAttribute("allQualifiedCandidates",
					candService.findAllCandidateWhoAppearedTheExamByTestTakerId(testTakerId));

		}if (testTakerId != 0 && subTetTakerId != 0 && cutoffMark == null) {
			model.addAttribute("allQualifiedCandidates", candService
					.findAllCandidateWhoAppearedTheExamtestTakerIdAndsubTetTakerId(testTakerId, subTetTakerId));
		}if (cutoffMark != null) {
			model.addAttribute("allQualifiedCandidates", candService.findAllCandidateByCutOff(cutoffMark));
		}if (testTakerId != 0 && subTetTakerId == 0 && cutoffMark != null) {
			model.addAttribute("allQualifiedCandidates",
					candService.findAllCandidateByCutOffByTestakerId(cutoffMark, testTakerId));
		} if (testTakerId != 0 && subTetTakerId != 0 && cutoffMark != null) {
			model.addAttribute("allQualifiedCandidates", candService
					.findAllCandidateByCutOffByTestakerIdAndSubTetTakerId(testTakerId, subTetTakerId, cutoffMark));
		}

		return "admin/qualifiedCandidates";

	}

	@GetMapping("/notQualifiedCandidatesReport")
	public String getNotQualifiedCandidatesReport(Model model) {
		model.addAttribute("allRegisterCandidates", candService.findAllCandidate());
		model.addAttribute("testTakerList", testTakerService.getAllCollege());
		httpSession.setAttribute("subtesttakr", null);
		httpSession.setAttribute("testtaker", null);
		return "admin/notQualifiedCandidates";

	}
	
	@GetMapping("/summaryReport")
	public String getSummaryReport(Model model) {
		
		model.addAttribute("testTakerList", testTakerService.getAllCollege());
		System.out.println(testTakerService.findAllCollegeName());
		System.out.println(candService.totalRegisteredCandidatesInCollege());
		model.addAttribute("allCollege", testTakerService.findAllCollegeName());
		model.addAttribute("totalCandByCollege", candService.totalRegisteredCandidatesInCollege());
		model.addAttribute("totalAppearedCandidate", candService.getApperaedCandidatesInCollege());
		System.out.println(candService.getApperaedCandidatesInCollege());
		System.out.println(candService.getQualifiedCandidatesInCollege());
		model.addAttribute("totalQualified", candService.getQualifiedCandidatesInCollege());
		
		return "admin/summaryReport";
		
	}
	/*
	 * @GetMapping("/yearlyReport") public ModelAndView getYearlyReport(Model model)
	 * { model.addAttribute("testTakerList", testTakerService.getAll());
	 * System.out.println(candService.getAllCountByCollege());
	 * //model.addAttribute("allDetailsByCollege", ); List<Map<String, Object>> data
	 * = candService.getAllCountByCollege();
	 * 
	 * ModelAndView modelAndView = new ModelAndView("admin/yearlyReport"); //
	 * Specify your JSP page name here modelAndView.addObject("queryResult", data);
	 * // Add the data to the model
	 * 
	 * return modelAndView;
	 * 
	 * 
	 * }
	 */
	
	 @GetMapping("/yearlyReport")
	    public String getAllDetailsByCollege(Model model) {
		 	
		  LocalDate currentDate = LocalDate.now();
	        
	        // Determine the start date of the current session (April 1st of the current year)
	        LocalDate startDate = LocalDate.of(currentDate.getYear(), Month.APRIL, 1);
	        
	        // Determine the end date of the current session (March 31st of the following year)
	        LocalDate endDate = LocalDate.of(currentDate.getYear()+1, Month.MARCH, 31);
	        
	        // Convert the dates to string format
	        String startDateStr = startDate.toString();
	        String endDateStr = endDate.toString();
	        
	        // Output the current session dates
	        System.out.println("Current session start date: " + startDateStr);
	        System.out.println("Current session end date: " + endDateStr);
		    String session =  startDateStr+"-"+endDateStr;
		    httpSession.setAttribute("session", session);
		    //httpSession.setAttribute("collegeValue", 0);
		    model.addAttribute("testTakerList", testTakerService.getAllCollege());
		    
		    //List<Map<String, Object>> queryResult = candService.getAllCountByCollegeBySession(startDate, endDate);
		    
		    List<Map<String, Object>> queryResult = candService.getAllCountByCollegeBySession(startDateStr, endDateStr);
		    
		    System.out.println("gere coming yearly report");
		    List<Object[]> data = new ArrayList<>();

		    for (Map<String, Object> map : queryResult) {
		        Object[] row = new Object[7]; // Assuming you have 7 columns
		        row[0] = map.get("test_taker_id");
		        row[1] = map.get("CollegeName");
		        row[2] = map.get("BatchName");
		        row[3] = (map.get("test_date") != null) ? map.get("test_date").toString() : "NA"; // Assuming test_date is a Date object
		        row[4] = map.get("total_students");
		        row[5] = map.get("appeared_students");
		        row[6] = map.get("qualified_students");

		        data.add(row);
		    }		    System.out.println(data);
		    model.addAttribute("queryResult", data);
		    
	        return "admin/misReport"; // Return the name of your JSP page
	    }
	 
	
	 @PostMapping("searchYear")
	 public String getYearlyReportBySession(@RequestParam(name="yearDropdown",required = false) String dateRange ,@RequestParam(name="testTakerName",required = false) Integer testTakerId,Model model) {
		 
		 model.addAttribute("testTakerList", testTakerService.getAllCollege());
		 System.out.println("the year");
		 System.out.println(dateRange+"///////////////////////////"+testTakerId);
		 httpSession.setAttribute("session", dateRange);
		 httpSession.setAttribute("testtaker", testTakerId);
		 if (testTakerId == 0 && dateRange.equals("")) {
			 System.out.println("/for all");
			    List<Map<String, Object>> queryResult = candService.getAllCountByCollege();
			    List<Object[]> data = new ArrayList<>();

			    for (Map<String, Object> map : queryResult) {
			        Object[] row = new Object[7]; // Assuming you have 7 columns
			        row[0] = map.get("test_taker_id");
			        row[1] = map.get("CollegeName");
			        row[2] = map.get("BatchName");
			        row[3] = (map.get("test_date") != null) ? map.get("test_date").toString() : "NA"; // Assuming test_date is a Date object
			        row[4] = map.get("total_students");
			        row[5] = map.get("appeared_students");
			        row[6] = map.get("qualified_students");

			        data.add(row);
			    }		    System.out.println(data);
			    model.addAttribute("queryResult", data);
			} else if (!dateRange.equals("") && testTakerId == 0) {
			    String[] dates = dateRange.split(" - ");
			    String startDate = dates[0];
			    String endDate = dates[1];
			    List<Map<String, Object>> queryResult = candService.getAllCountByCollegeBySession(startDate, endDate);
			    List<Object[]> data = new ArrayList<>();

			    for (Map<String, Object> map : queryResult) {
			        Object[] row = new Object[7]; // Assuming you have 7 columns
			        row[0] = map.get("test_taker_id");
			        row[1] = map.get("CollegeName");
			        row[2] = map.get("BatchName");
			        row[3] = (map.get("test_date") != null) ? map.get("test_date").toString() : "NA"; // Assuming test_date is a Date object
			        row[4] = map.get("total_students");
			        row[5] = map.get("appeared_students");
			        row[6] = map.get("qualified_students");

			        data.add(row);
			    }		    System.out.println(data);
			    model.addAttribute("queryResult", data);
			    
			} else if (dateRange.equals("") && testTakerId != 0) {
			    List<Map<String, Object>> queryResult = candService.getAllCountByCollegeByTestTakerId(testTakerId);
			    List<Object[]> data = new ArrayList<>();

			    for (Map<String, Object> map : queryResult) {
			        Object[] row = new Object[7]; // Assuming you have 7 columns
			        row[0] = map.get("test_taker_id");
			        row[1] = map.get("CollegeName");
			        row[2] = map.get("BatchName");
			        row[3] = (map.get("test_date") != null) ? map.get("test_date").toString() : "NA"; // Assuming test_date is a Date object
			        row[4] = map.get("total_students");
			        row[5] = map.get("appeared_students");
			        row[6] = map.get("qualified_students");

			        data.add(row);
			    }		    System.out.println(data);
			    model.addAttribute("queryResult", data);
			} else if (!dateRange.equals("") && testTakerId != 0) {
			    String[] dates = dateRange.split(" - ");
			    String startDate = dates[0];
			    String endDate = dates[1];
			    List<Map<String, Object>> queryResult = candService.getAllCountByCollegeBySessionAndTestTakerId(startDate, endDate, testTakerId);
			    List<Object[]> data = new ArrayList<>();

			    for (Map<String, Object> map : queryResult) {
			        Object[] row = new Object[7]; // Assuming you have 7 columns
			        row[0] = map.get("test_taker_id");
			        row[1] = map.get("CollegeName");
			        row[2] = map.get("BatchName");
			        row[3] = (map.get("test_date") != null) ? map.get("test_date").toString() : "NA"; // Assuming test_date is a Date object
			        row[4] = map.get("total_students");
			        row[5] = map.get("appeared_students");
			        row[6] = map.get("qualified_students");

			        data.add(row);
			    }		    System.out.println(data);
			    model.addAttribute("queryResult", data);
			} else {
			    // Handle the case where neither condition is met
			}

		 
		 
		return "admin/misReport";
		 
	 }
	 
		

}
