package io.codeka.gaia.modules.repository;

import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.registries.RegistryRawContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TerraformModuleGitRepositoryTest {

    @Mock
    RegistryRawContent registryRawContent;

    private TerraformModuleGitRepository repository;

    @BeforeEach
    void setup() {
        repository = new TerraformModuleGitRepository(List.of(registryRawContent));
    }

    @Test
    void getReadme_shouldReturnReadme() {
        // given
        var module = new TerraformModule();
        module.setGitRepositoryUrl("url");
        module.setGitBranch("branch");
        module.setDirectory("directory");

        // when
        when(registryRawContent.matches(anyString())).thenReturn(true);
        when(registryRawContent.getRawUrl(anyString(), anyString(), anyString())).thenReturn("raw_url");
        var result = repository.getReadme(module);

        // then
        assertThat(result).isPresent().get().isEqualTo("raw_url/README.md");
        verify(registryRawContent).matches("url");
        verify(registryRawContent).getRawUrl("url", "branch", "directory");
    }

    @Test
    void getReadme_shouldReturnNothingIfNoStrategyFound() {
        // given
        var module = new TerraformModule();

        // when
        when(registryRawContent.matches(any())).thenReturn(false);
        var result = repository.getReadme(module);

        // then
        assertThat(result).isEmpty();
    }
}