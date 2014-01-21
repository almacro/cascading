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

package cascading.flow.planner.rule;

import cascading.flow.planner.FlowElementGraph;
import cascading.flow.planner.PlannerContext;
import cascading.flow.planner.iso.transformer.ContractedTransformer;
import cascading.flow.planner.iso.transformer.GraphTransformer;
import cascading.flow.planner.iso.transformer.RecursiveGraphTransformer;
import cascading.flow.planner.iso.transformer.SubGraphTransformer;
import cascading.flow.planner.iso.transformer.Transform;

/**
 *
 */
public class RuleTransformer extends GraphTransformer<FlowElementGraph, FlowElementGraph> implements Rule
  {
  private final PlanPhase phase;
  protected final RuleExpression ruleExpression;
  protected final ContractedTransformer contractedTransformer;
  protected final SubGraphTransformer subGraphTransformer;

  protected RecursiveGraphTransformer<FlowElementGraph> flowGraphTransformer;

  public RuleTransformer( PlanPhase phase, RuleExpression ruleExpression )
    {
    this.phase = phase;
    this.ruleExpression = ruleExpression;

    if( ruleExpression.getContractionExpression() != null )
      contractedTransformer = new ContractedTransformer( ruleExpression.getContractionExpression() );
    else
      contractedTransformer = null;

    if( ruleExpression.getContractedMatchExpression() != null )
      {
      if( contractedTransformer == null )
        throw new IllegalArgumentException( "must have contracted expression if given contracted match expression" );

      subGraphTransformer = new SubGraphTransformer( contractedTransformer, ruleExpression.getContractedMatchExpression() );
      }
    else
      {
      subGraphTransformer = null;
      }
    }

  @Override
  public PlanPhase getRulePhase()
    {
    return phase;
    }

  @Override
  public String getRuleName()
    {
    return getClass().getSimpleName().replaceAll( "^(.*)[]A-Z][a-z]*Rule$", "$1" );
    }

  @Override
  public Transform<FlowElementGraph> transform( PlannerContext plannerContext, FlowElementGraph rootGraph )
    {
    Transform<FlowElementGraph> result = new Transform<>( plannerContext, this, rootGraph );

    FlowElementGraph graphCopy = new FlowElementGraph( rootGraph );

    Transform<FlowElementGraph> transform = flowGraphTransformer.transform( plannerContext, graphCopy );

    result.addChildTransform( transform );

    if( rootGraph != transform.getEndGraph() )
      result.setEndGraph( transform.getEndGraph() );

    return result;
    }
  }
