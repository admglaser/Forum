package hu.bme.aut.onlab.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.dao.ForumDao;
import hu.bme.aut.onlab.dao.model.CategoryDao;
import hu.bme.aut.onlab.dto.out.home.CategoryHomeDto;
import hu.bme.aut.onlab.dto.out.home.HomeDto;
import hu.bme.aut.onlab.dto.out.home.SubcategoryHomeDto;
import hu.bme.aut.onlab.model.Category;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.model.Post;
import hu.bme.aut.onlab.model.Subcategory;
import hu.bme.aut.onlab.model.Topic;
import hu.bme.aut.onlab.util.Formatter;
import hu.bme.aut.onlab.util.LinkUtils;
import hu.bme.aut.onlab.util.NavigationUtils;

@LocalBean
@Stateless
public class HomeService {

	@EJB
	private CategoryDao categoryDao;
	
	@EJB
	private ForumDao forumDao;

	public HomeDto getHome(Member member) {
		HomeDto homeDto = new HomeDto();

		homeDto.setLoggedIn(member != null);
		List<CategoryHomeDto> categoriesDto = new ArrayList<>();
		homeDto.setCategories(categoriesDto);
		
		List<Category> categories = categoryDao.findAllEntity();
		for (Category category : categories) {
			boolean canViewCategory = false;

			CategoryHomeDto categoryDto = new CategoryHomeDto();
			categoryDto.setTitle(category.getTitle());
			List<SubcategoryHomeDto> subcategoriesDto = new ArrayList<>();
			categoryDto.setSubcategories(subcategoriesDto);
			for (Subcategory subcategory : category.getSubcategories()) {
				boolean canViewSubcategory = forumDao.canMemberViewSubcategory(member, subcategory);

				if (canViewSubcategory) {
					if (!canViewCategory) {
						canViewCategory = true;
					}
					SubcategoryHomeDto subcategoryDto = new SubcategoryHomeDto();
					Collection<Topic> topics = subcategory.getTopics();

					int totalPosts = 0;
					for (Topic topic : topics) {
						totalPosts += topic.getPosts().size();
					}

					subcategoryDto.setTitle(subcategory.getTitle());
					subcategoryDto.setDesc(subcategory.getDesc());
					subcategoryDto.setTopicCount(topics.size());
					subcategoryDto.setPostCount(totalPosts);
					subcategoryDto.setHasLastTopic(!topics.isEmpty());
					subcategoryDto.setSubcategoryLink("#/category/" + subcategory.getId());
					if (!topics.isEmpty()) {
						Topic lastTopic = forumDao.getTopicWithLastPostFromSubcategory(subcategory);
						Post lastPost = forumDao.getLastPostFromTopic(lastTopic);
						Member memberOfLastPost = lastPost.getMember();

						subcategoryDto.setLastTitle(lastTopic.getTitle());
						subcategoryDto.setLastPoster(memberOfLastPost.getDisplayName());
						subcategoryDto.setLastDate(Formatter.formatTimeStamp(lastPost.getTime()));
						subcategoryDto.setLastPosterImageLink(LinkUtils.getProfilePictureLink(memberOfLastPost.getPictureId()));
						subcategoryDto.setTopicLink("#/topic/" + lastTopic.getId());
						subcategoryDto.setPostLink("#/topic/" + lastTopic.getId() + "/" + NavigationUtils.getPageOfElement(lastPost.getPostNumber()));
						subcategoryDto.setUserLink("#/user/" + memberOfLastPost.getId());
						subcategoryDto.setUnread(member == null ? false	: forumDao.hasTopicUnreadPostsByMember(lastTopic, member));
						subcategoryDto.setLastPosterStyle(Formatter.getMemberStyle(memberOfLastPost));
					}
					subcategoriesDto.add(subcategoryDto);
				}
			}
			if (canViewCategory) {
				categoriesDto.add(categoryDto);
			}
		}
		return homeDto;
	}

}
