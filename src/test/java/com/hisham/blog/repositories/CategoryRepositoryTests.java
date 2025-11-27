package com.hisham.blog.repositories;

import com.hisham.blog.domain.entities.Category;
import com.hisham.blog.domain.entities.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    Category category1;
    Category category2;

    @BeforeEach
    public void init(){
        category1 = Category.builder()
                .name("Science")
                .build();

        category2 = Category.builder()
                .name("Medical")
                .build();
    }

    @Test
    public void givenExistingCategories_whenFindAllWithPostContent_thenReturnAllWithPostCount(){
        List<Post> posts = new ArrayList<>();
        posts.add(new Post());
        posts.add(new Post());
        posts.add(new Post());

        category1.setPosts(posts);
        category2.setPosts(posts);

        categoryRepository.save(category1);
        categoryRepository.save(category2);

        List<Category> categories = categoryRepository.findAllWithPostCount();

        Assertions.assertThat(categories).isNotNull();
        Assertions.assertThat(categories.size()).isEqualTo(2);
        Assertions.assertThat(categories)
                .extracting(Category::getName)
                .containsExactly("Science","Medical");
        Assertions.assertThat(categories)
                .extracting(c -> c.getPosts().size())
                .containsExactlyInAnyOrder(3,3);
    }
}
