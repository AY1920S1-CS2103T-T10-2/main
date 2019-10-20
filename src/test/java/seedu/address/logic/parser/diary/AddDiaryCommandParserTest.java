package seedu.address.logic.parser.diary;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.diary.TypicalDiaries.AMY_DIARY;
import static seedu.address.testutil.diary.TypicalDiaries.BOB_DIARY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.diary.AddDiaryCommand;
import seedu.address.model.diary.components.Diary;
import seedu.address.model.diary.components.DiaryName;
import seedu.address.testutil.diary.DiaryBuilder;

public class AddDiaryCommandParserTest {
    private AddDiaryCommandParser parser = new AddDiaryCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Diary expectedDiary = new DiaryBuilder(BOB_DIARY).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB, new AddDiaryCommand(expectedDiary));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB, new AddDiaryCommand(expectedDiary));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Diary expectedDiary = new DiaryBuilder(AMY_DIARY).build();
        assertParseSuccess(parser, NAME_DESC_AMY, new AddDiaryCommand(expectedDiary));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDiaryCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC, DiaryName.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC, DiaryName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDiaryCommand.MESSAGE_USAGE));
    }
}