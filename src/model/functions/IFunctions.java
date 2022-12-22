package model.functions;

import model.IModel;

/**
 * Represents generic functions that can be applied to models.
 */
public interface IFunctions {

  /**
   * Performs an action on the given model. Unique in each function object class.
   * @param model the model to be used for the action.
   * @return a new model resulting from the action.
   */
  IModel apply(IModel model);

}