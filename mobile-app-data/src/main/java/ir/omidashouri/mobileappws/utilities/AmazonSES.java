package ir.omidashouri.mobileappws.utilities;

import ir.omidashouri.mobileappws.domain.User;

public class AmazonSES {

    final String FROM = "omidashouri@gmail.com";

    final String SUBJECT = "one last step to complete your registration with PhotoApp";

//    The HTML body for the email.
    final String HTMLBODY = "<h1>Please verify your emal address</h1>" +
            "<p>Thank you for registering with our application. To complete registration process do following</p>" +
            "click on the following link:" +
            "<a href='http://localhost:8080/v1/users/email-verification?token=$tokenValue'>" +
            "Final step to complete your registration" +"</a><br/><br/>" +
            "Thank you! And we are waiting for you inside!";

//    The email body for recipients with non-HTML email clients.
    final String TEXTBODY = "Please verify your email address. " +
            "<p>Thank you for registering with our application. To complete registration process do following</p>" +
            "Open the following URL in your browser window: " +
            " http://localhost:8080/v1/users/email-verification?token=$tokenValue " +
            "Thank you! And we are waiting for you inside!";

//    use this method in 'UserServiceImpl' -> 'createUserDto' method
    public void verifyEmail(User userDomain){

        String htmlBodyWithToken = HTMLBODY.replace("$tokenValue",userDomain.getEmailVerificationToken());
        String textBodyWithToken = TEXTBODY.replace("$tokenValue",userDomain.getEmailVerificationToken());

//        Amazon Class
/*        SendEmailRequest request = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(userDomain.getEmail()))
                .withMessage(new Message()
                        .withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBodyWithToken))
                                            .withText(new Content().withCharset("UTF-8").withData(textBodyWithToken)))
                .withSubject(new Content().withCharset("UTF-8").withData(SUBJECT)))
                .withSource(FROM);

        client.sendEmail(request);*/

        System.out.println("Email Sent!");
    }
}
