package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job aJob = jobData.findById(id);
        //need to pass aJob into view.
        model.addAttribute("jobForm", aJob);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(@ModelAttribute @Valid JobForm jobForm, Errors errors, Model model) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        //IN OTHER WORDS:
        // Validate the form in the add handler of JobController, and if it's valid,
        // create a new Job object and add it to the data layer by calling jobData.add(newJob).
        //
        //To create the new job, you'll need to find the pre-existing objects for
        // all fields other than name (employer, location, etc).
        // Do this using the methods discussed above.
        // Refer to the constructor in Job to make sure you list the objects in the correct order when calling it.


        if (errors.hasErrors()){
            return "new-job"; //if errors, reload form WORKS
        }

        String name = jobForm.getName();
        Employer employerName  = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location location = jobData.getLocations().findById(jobForm.getLocationId());
        PositionType positionType = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());
        CoreCompetency coreCompetency = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());
        Job newJob = new Job(name, employerName, location, positionType, coreCompetency);
        jobData.add(newJob);
        return "redirect:/job?id=" + newJob.getId();
    }
}