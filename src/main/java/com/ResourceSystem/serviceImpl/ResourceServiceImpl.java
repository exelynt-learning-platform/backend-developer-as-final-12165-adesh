package com.ResourceSystem.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ResourceSystem.dto.ResourceRequest;
import com.ResourceSystem.dto.ResourceResponse;
import com.ResourceSystem.entity.Resource;
import com.ResourceSystem.exception.ResourceNotFoundException;
import com.ResourceSystem.repository.ResourceRepository;
import com.ResourceSystem.service.ResourceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
        private final ResourceRepository resourceRepository;

        @Override
        public ResourceResponse createResource(ResourceRequest request) {
                Resource resource = Resource.builder()

                                .name(request.getName())

                                .description(request.getDescription())

                                .location(request.getLocation())

                                .type(request.getType())

                                .pricePerHour(request.getPricePerHour())

                                .available(request.getAvailable())

                                .build();

                Resource savedResource = resourceRepository.save(resource);

                return mapToResponse(savedResource);

        }

        @Override
        public ResourceResponse updateResource(Long id, ResourceRequest request) {
                // TODO Auto-generated method stub
                Resource resource = resourceRepository.findById(id)

                                .orElseThrow(
                                                () -> new ResourceNotFoundException(
                                                                "Resource not found with id : "
                                                                                + id));
                resource.setName(
                                request.getName());

                resource.setDescription(
                                request.getDescription());

                resource.setType(
                                request.getType());

                resource.setPricePerHour(
                                request.getPricePerHour());

                resource.setAvailable(
                                request.getAvailable());

                resource.setLocation(request.getLocation());

                Resource updatedResource = resourceRepository.save(resource);

                return mapToResponse(updatedResource);

        }

        @Override
        public ResourceResponse getResourceById(
                        Long resourceId) {

                Resource resource = resourceRepository.findById(resourceId)

                                .orElseThrow(
                                                () -> new ResourceNotFoundException(
                                                                "Resource not found with id : "
                                                                                + resourceId));

                return mapToResponse(resource);

        }

        @Override
        public List<ResourceResponse> getAllResources() {

                return resourceRepository
                                .findAll()
                                .stream()

                                .map(this::mapToResponse)

                                .collect(Collectors.toList());

        }

        @Override
        public void deleteResource(Long resourceId) {

                Resource resource = resourceRepository.findById(resourceId)

                                .orElseThrow(
                                                () -> new ResourceNotFoundException(
                                                                "Resource not found with id : "
                                                                                + resourceId));

                resourceRepository.delete(resource);

        }

        private ResourceResponse mapToResponse(
                        Resource resource) {

                return ResourceResponse.builder()

                                .id(resource.getId())

                                .name(resource.getName())

                                .type(resource.getType())

                                .description(resource.getDescription())

                                .location(resource.getLocation())

                                .price(resource.getPricePerHour())

                                .available(resource.getAvailable())

                                .build();

        }
}
