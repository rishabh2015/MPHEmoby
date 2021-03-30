package com.emoby.mph.config;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.emoby.mph.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.emoby.mph.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.emoby.mph.domain.User.class.getName());
            createCache(cm, com.emoby.mph.domain.Authority.class.getName());
            createCache(cm, com.emoby.mph.domain.User.class.getName() + ".authorities");
            createCache(cm, com.emoby.mph.domain.User.class.getName() + ".sectors");
            createCache(cm, com.emoby.mph.domain.Experience.class.getName());
            createCache(cm, com.emoby.mph.domain.Educationlevel.class.getName());
            createCache(cm, com.emoby.mph.domain.Language.class.getName());
            createCache(cm, com.emoby.mph.domain.Sector.class.getName());
            createCache(cm, com.emoby.mph.domain.Project.class.getName());
            createCache(cm, com.emoby.mph.domain.Jobdescription.class.getName());
            createCache(cm, com.emoby.mph.domain.Project.class.getName() + ".jobdescriptions");
            createCache(cm, com.emoby.mph.domain.Projectphase.class.getName());
            createCache(cm, com.emoby.mph.domain.Subsector.class.getName());
            createCache(cm, com.emoby.mph.domain.SectorSubsector.class.getName());
            createCache(cm, com.emoby.mph.domain.Activity.class.getName());
            createCache(cm, com.emoby.mph.domain.ProjectphaseActivity.class.getName());
            createCache(cm, com.emoby.mph.domain.TechnicalDiscipline.class.getName());
            createCache(cm, com.emoby.mph.domain.Country.class.getName());
            createCache(cm, com.emoby.mph.domain.Jobdescription.class.getName() + ".nationalities");
            createCache(cm, com.emoby.mph.domain.Jobdescription.class.getName() + ".locations");
            createCache(cm, com.emoby.mph.domain.Jobdescription.class.getName() + ".educationlevels");
            createCache(cm, com.emoby.mph.domain.Jobdescription.class.getName() + ".projectphaseactivities");
            createCache(cm, com.emoby.mph.domain.Jobdescription.class.getName() + ".technicaldisciplines");
            createCache(cm, com.emoby.mph.domain.Jobdescription.class.getName() + ".experiences");
            createCache(cm, com.emoby.mph.domain.Jobdescription.class.getName() + ".languages");
            createCache(cm, com.emoby.mph.domain.Jobdescription.class.getName() + ".sectorsubsectors");
            createCache(cm, com.emoby.mph.domain.Jobdescription.class.getName() + ".projectphaseActivities");
            createCache(cm, com.emoby.mph.domain.Jobdescription.class.getName() + ".technicalDisciplines");
            createCache(cm, com.emoby.mph.domain.Jobdescription.class.getName() + ".sectorSubsectors");
            createCache(cm, com.emoby.mph.domain.Jobdescription.class.getName() + ".sectors");
            createCache(cm, com.emoby.mph.domain.Content.class.getName());
            createCache(cm, com.emoby.mph.domain.PotentialCandidate.class.getName());
            createCache(cm, com.emoby.mph.domain.LevelLanguage.class.getName());
            createCache(cm, com.emoby.mph.domain.Candidate.class.getName());
            createCache(cm, com.emoby.mph.domain.Candidate.class.getName() + ".dual_nationalities");
            createCache(cm, com.emoby.mph.domain.Candidate.class.getName() + ".sectorsubsectors");
            createCache(cm, com.emoby.mph.domain.Candidate.class.getName() + ".projectphaseActivities");
            createCache(cm, com.emoby.mph.domain.Candidate.class.getName() + ".technicalDisciplines");
            createCache(cm, com.emoby.mph.domain.Candidate.class.getName() + ".dualNationalities");
            createCache(cm, com.emoby.mph.domain.Candidate.class.getName() + ".sectorSubsectors");
            createCache(cm, com.emoby.mph.domain.CandidateLevelLanguage.class.getName());
            createCache(cm, com.emoby.mph.domain.Candidate.class.getName() + ".levelLanguages");
            createCache(cm, com.emoby.mph.domain.JobOpening.class.getName());
            createCache(cm, com.emoby.mph.domain.MobyStatus.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
