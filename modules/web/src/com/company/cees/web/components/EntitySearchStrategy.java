package com.company.cees.web.components;

import com.haulmont.addon.search.context.SearchContext;
import com.haulmont.addon.search.strategy.DefaultSearchEntry;
import com.haulmont.addon.search.strategy.SearchEntry;
import com.haulmont.addon.search.strategy.SearchStrategy;
import com.haulmont.bali.util.ParamsMap;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.MessageTools;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.core.global.Security;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.security.entity.EntityOp;
import com.haulmont.cuba.web.AppUI;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

@Component("cees_EntitySearchStrategy")
public class EntitySearchStrategy implements SearchStrategy {

  private static final String ENTITY_CONTENT_SEPARATOR = " ";

  @Nonnull
  @Override
  public String name() {
    return "entitySearchStrategy";
  }

  @Inject
  Metadata metadata;


  @Inject
  Messages messages;

  @Inject
  Security security;


  @Nonnull
  @Override
  public List<SearchEntry> load(@Nonnull SearchContext context, String query) {
    return findMatchingMetaClasses(query);
  }


  @Override
  public void invoke(@Nonnull SearchContext context, SearchEntry value) {
    DefaultSearchEntry searchEntry = (DefaultSearchEntry) value;

    AppUI.getCurrent()
        .getTopLevelWindow()
        .openWindow(searchEntry.getId() + ".browse", WindowManager.OpenType.NEW_TAB);
  }

  protected boolean readPermitted(MetaClass metaClass) {
    return entityOpPermitted(metaClass, EntityOp.READ);
  }

  protected boolean entityOpPermitted(MetaClass metaClass, EntityOp entityOp) {
    return security.isEntityOpPermitted(metaClass, entityOp);
  }


  private MessageTools getMessageTools() {
    return messages.getTools();
  }



  private List<SearchEntry> findMatchingMetaClasses(String query) {

    List<MetaClass> allPersistentMetaClasses = getPersistentMetaClasses();

    return allPersistentMetaClasses.stream()
        .filter(metaClass -> matchesMetaClass(query, metaClass))
        .map(metaClass -> createSearchEntry(query, metaClass))
        .collect(Collectors.toList());
  }

  private SearchEntry createSearchEntry(String query, MetaClass metaClass) {
    return new DefaultSearchEntry(
        metaClass.getName(),
        query,
        getMessageTools().getEntityCaption(metaClass),
        name()
    );
  }

  private boolean matchesMetaClass(String query, MetaClass metaClass) {
    String entityCaption = getMessageTools().getEntityCaption(metaClass).toLowerCase();

    String entityQuery = getMetaClassNamePart(query);

    return entityCaption.contains(entityQuery);
  }

  private String getMetaClassNamePart(String query) {
    return query.split(ENTITY_CONTENT_SEPARATOR)[0].toLowerCase();
  }

  private List<MetaClass> getPersistentMetaClasses() {
    List<MetaClass> metaClasses = new LinkedList<>();

    for (MetaClass metaClass : getMetadataTools().getAllPersistentMetaClasses()) {
      if (readPermitted(metaClass) && !getMetadataTools().isSystemLevel(metaClass)) {
        if (Entity.class.isAssignableFrom(metaClass.getJavaClass())) {
          metaClasses.add(metaClass);
        }
      }
    }
    return metaClasses;
  }

  private MetadataTools getMetadataTools() {
    return metadata.getTools();
  }


}
