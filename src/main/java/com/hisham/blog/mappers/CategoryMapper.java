package com.hisham.blog.mappers;

import com.hisham.blog.domain.dtos.CategoryDto;
import com.hisham.blog.domain.dtos.CreateCategoryRequest;
import com.hisham.blog.domain.entities.Category;
import com.hisham.blog.domain.entities.Post;
import com.hisham.blog.domain.entities.PostStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(target="postCount",source = "posts", qualifiedByName = "calculatePostCount")
    CategoryDto toDto (Category category);

    Category toEntity(CreateCategoryRequest createCategoryRequest);

    @Named("calculatePostCount")
    default long calculatePostCount(List<Post> posts){
        if(posts == null){
            return 0;
        }
        return posts.stream()
                .filter(post -> post.getStatus().equals(PostStatus.PUBLISHED)).count();
    }
}
