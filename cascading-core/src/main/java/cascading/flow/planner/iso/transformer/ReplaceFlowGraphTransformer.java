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

package cascading.flow.planner.iso.transformer;

import java.util.Set;

import cascading.flow.FlowElement;
import cascading.flow.planner.FlowElementGraph;
import cascading.flow.planner.iso.expression.ElementExpression;
import cascading.flow.planner.iso.expression.ExpressionGraph;
import cascading.flow.planner.iso.finder.Match;
import cascading.util.Util;

/**
 *
 */
public class ReplaceFlowGraphTransformer extends MutateFlowGraphTransformer
  {
  public ReplaceFlowGraphTransformer( ExpressionGraph filter )
    {
    super( filter );
    }

  public ReplaceFlowGraphTransformer( GraphTransformer graphTransformer, ExpressionGraph filter )
    {
    super( graphTransformer, filter );
    }

  @Override
  protected boolean transformGraphInPlaceUsing( Transform<FlowElementGraph> transform, FlowElementGraph graph, Match match )
    {
    Set<FlowElement> replace = match.getCapturedElements( ElementExpression.Capture.Primary );
    Set<FlowElement> replaceWith = match.getCapturedElements( ElementExpression.Capture.Secondary );

    if( replace.isEmpty() || replaceWith.isEmpty() )
      return false;

    if( replace.size() != 1 )
      throw new IllegalStateException( "too many captured elements" );

    if( replaceWith.size() != 1 )
      throw new IllegalStateException( "too many target elements" );

    graph.replaceElementWith( Util.getFirst( replace ), Util.getFirst( replaceWith ) );

    return true;
    }
  }
