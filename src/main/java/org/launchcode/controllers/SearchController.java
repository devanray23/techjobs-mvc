package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices);

        return "search";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String searchJobs(Model model, HttpServletRequest request) {

        String searchType = request.getParameter("searchType");
        String searchTerm = request.getParameter("searchTerm");
        ArrayList<HashMap<String, String>> jobsList;

        if(searchType == "all" && searchTerm == null){
            jobsList = JobData.findAll();
            model.addAttribute("jobsList", jobsList);

        }else if(searchType == "all" && searchTerm != null){
            jobsList = JobData.findByValue(searchTerm);
            model.addAttribute("jobsList", jobsList);

        }else{
            jobsList = JobData.findByColumnAndValue(searchType, searchTerm);
            model.addAttribute("jobsList", jobsList);
        }

        model.addAttribute("results",
                Integer.toString(jobsList.size()) + " Result(s)");

        return "search";
    }

}
