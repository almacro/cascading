/*
 * Copyright (c) 2007-2014 Concurrent, Inc. All Rights Reserved.
 *
 * Project and contact information: http://www.cascading.org/
 *
 * This file is part of the Cascading project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cascading.flow.planner.iso.finder;

import java.util.HashSet;
import java.util.Set;

import cascading.flow.FlowElement;
import cascading.flow.planner.Scope;

/**
 *
 */
class FinderContext
  {
  Set<FlowElement> excludedElements;
  Set<FlowElement> ignoredElements;
  Set<FlowElement> requiredElements;
  Set<FlowElement> foundElements;
  Set<Scope> foundScopes;

  public FinderContext( Set<FlowElement> excludes )
    {
    getExcludedElements().addAll( excludes );
    }

  public FinderContext()
    {
    }

  public Set<FlowElement> getExcludedElements()
    {
    if( excludedElements == null )
      excludedElements = new HashSet<>();

    return excludedElements;
    }

  public boolean isExcluded( FlowElement flowElement )
    {
    return getExcludedElements().contains( flowElement );
    }

  public Set<FlowElement> getRequiredElements()
    {
    if( requiredElements == null )
      requiredElements = new HashSet<>();

    return requiredElements;
    }

  public boolean isRequired( FlowElement flowElement )
    {
    return getRequiredElements().contains( flowElement );
    }

  public Set<FlowElement> getFoundElements()
    {
    if( foundElements == null )
      foundElements = new HashSet<>();

    return foundElements;
    }

  public Set<Scope> getFoundScopes()
    {
    if( foundScopes == null )
      foundScopes = new HashSet<>();

    return foundScopes;
    }

  public Set<FlowElement> getIgnoredElements()
    {
    if( ignoredElements == null )
      ignoredElements = new HashSet<>();

    return ignoredElements;
    }

  public boolean isIgnored( FlowElement flowElement )
    {
    return getIgnoredElements().contains( flowElement );
    }
  }
