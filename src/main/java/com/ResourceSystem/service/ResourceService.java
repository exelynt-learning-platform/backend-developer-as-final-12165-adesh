package com.ResourceSystem.service;

import java.util.List;

import com.ResourceSystem.dto.ResourceRequest;
import com.ResourceSystem.dto.ResourceResponse;

public interface ResourceService {
    ResourceResponse createResource(ResourceRequest request);

    ResourceResponse updateResource(Long id, ResourceRequest request);

    ResourceResponse getResourceById(Long id);

    List<ResourceResponse> getAllResources();

    void deleteResource(Long id);
}
