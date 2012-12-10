/** This command class can show its result.
 * @author iamjoking
 */
package common.util.command;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import common.util.*;
import common.util.logging.*;

public class NoticeCommand extends LoggedCommand {
	private CanShow noticeBoard;
	
	public NoticeCommand (String owner, CanShow noticeBoard) {
		super(owner);
		this.noticeBoard = noticeBoard;
	}
	
	@Override
	protected void lineResultHandler(String content) {
		System.out.println(content);
		noticeBoard.append(content);
	}
}
