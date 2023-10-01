package warehouse.com.reststarter.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

  public static final String INVALID_SPECIAL_CHARACTERS_MESSAGE = "Invalid %s. Special characters are not allowed";
  public static final String INVALID_FIELD_FORMAT_MESSAGE = "Field '%s' has an invalid format.";
  public static final String SPECIAL_CHARACTER_VALIDATION_PATTERN = "[^?^\"'@#*$%&()}{\\[\\]|\\\\/~+=<>]*$";
}
