package hu.bme.aut.onlab.filter;

import java.io.IOException;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.spi.ResteasyProviderFactory;

import hu.bme.aut.onlab.dao.LoginDao;
import hu.bme.aut.onlab.model.Member;

@Provider
@ApplicationScoped
public class LoginFilter implements ContainerRequestFilter {

	@EJB
	LoginDao loginService;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String encodedUserPassword = requestContext.getHeaders().getFirst("Authorization");
		ResteasyProviderFactory.pushContext(Member.class, loginService.getMember(encodedUserPassword));
	}

}
