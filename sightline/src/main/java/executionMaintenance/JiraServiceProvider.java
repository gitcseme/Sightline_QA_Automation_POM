package executionMaintenance;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.Issue.FluentCreate;

public class JiraServiceProvider {

	public JiraClient jira;
	public String project;
	
	public JiraServiceProvider(String jiraUrl, String userName, String password, String project) {
		BasicCredentials credentials = new BasicCredentials(userName, password);
		jira = new JiraClient(jiraUrl, credentials);
		this.project = project;
	}
	
	public void createJiraTicket(String issueType, String summary, String description, String reporterName) {
		
		try {
			FluentCreate fluentCreate = jira.createIssue(project, issueType);
			fluentCreate.field(Field.SUMMARY,summary);
			fluentCreate.field(Field.DESCRIPTION, description);
			Issue newIssue = fluentCreate.execute();
			System.out.println("New Issue Created in Jira with ID : "+newIssue);			
		} catch (JiraException e) {			
			e.printStackTrace();
		}
	}
}
