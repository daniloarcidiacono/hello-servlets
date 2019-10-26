package com.objectway.stage.helloservlets.books.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.objectway.stage.helloservlets.books.dto.ErrorDTO;
import com.objectway.stage.helloservlets.books.dto.ValidationErrorDTO;
import com.objectway.stage.helloservlets.books.exception.HelloServletsValidationException;
import com.objectway.stage.helloservlets.books.model.Book;
import com.objectway.stage.helloservlets.books.service.BookService;
import com.objectway.stage.helloservlets.books.service.InMemoryBookService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Servlet handling operations on books.
 *
 * @see Book
 */
public class BooksServlet extends HttpServlet {
	private static final String APPLICATION_JSON = "application/json";
	private static final String ALL_BOOKS = "/books";

	private boolean debugMode = false;
	private final ObjectMapper mapper = new ObjectMapper();
	private final BookService bookService;
	private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
	private final Validator validator = validatorFactory.getValidator();

	public BooksServlet() {
		this(new InMemoryBookService());
	}

	// Dependency-Injection constructore needed for tests
	public BooksServlet(final BookService bookService) {
		super();
		this.bookService = bookService;
	}

	@Override
	public void init() throws ServletException {
		super.init();
		debugMode = Boolean.valueOf(getServletContext().getInitParameter("debugMode"));
	}

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) {
		PrintWriter responseWriter = null;
		try {
			responseWriter = response.getWriter();
			response.setContentType(APPLICATION_JSON);

			if (ALL_BOOKS.equals(request.getServletPath())) {
				final String isbn = request.getParameter("isbn");
				if (isbn != null) {
					doSearchBookByISBN(request, response, responseWriter, isbn);
				} else {
					doGetAllBooks(request, response, responseWriter);
				}
			}
		} catch (Exception ex) {
			// Uncaught exception
			ex.printStackTrace();
			sendError("Internal server error", ex, 500, response, responseWriter);
		} finally {
			if (responseWriter != null) {
				responseWriter.close();
			}
		}
	}

	@Override
	protected void doPut(final HttpServletRequest request, final HttpServletResponse response) {
		PrintWriter responseWriter = null;
		try {
			responseWriter = response.getWriter();
			response.setContentType(APPLICATION_JSON);

			if (ALL_BOOKS.equals(request.getServletPath())) {
				final String serializedBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
				final Book book = mapper.readValue(serializedBody, Book.class);


				final Set<ConstraintViolation<Object>> violations = validator.validate(book);
				if (!violations.isEmpty()) {
					throw new HelloServletsValidationException("Book is not valid", violations);
				}
				doPutBook(request, response, responseWriter, book);
			}
		} catch (HelloServletsValidationException ex) {
			sendError(new ValidationErrorDTO("Book is not valid", ex.getViolations()), 400, response, responseWriter);
		} catch (Exception ex) {
			// Uncaught exception
			ex.printStackTrace();
			sendError("Internal server error", ex, 500, response, responseWriter);
		} finally {
			if (responseWriter != null) {
				responseWriter.close();
			}
		}
	}

	@Override
	protected void doDelete(final HttpServletRequest request, final HttpServletResponse response) {
		PrintWriter responseWriter = null;
		try {
			responseWriter = response.getWriter();
			response.setContentType(APPLICATION_JSON);

			if (ALL_BOOKS.equals(request.getServletPath())) {
				final String isbn = request.getParameter("isbn");
				if (isbn != null) {
					doDeleteBookByISBN(request, response, responseWriter, isbn);
				}
			}
		} catch (Exception ex) {
			// Uncaught exception
			ex.printStackTrace();
			sendError("Internal server error", ex, 500, response, responseWriter);
		} finally {
			if (responseWriter != null) {
				responseWriter.close();
			}
		}
	}

	private void doGetAllBooks(final HttpServletRequest request,
							   final HttpServletResponse response,
							   final PrintWriter responseWriter) throws JsonProcessingException {
		final List<Book> books = bookService.getAllBooks();
		final String serialized = mapper.writeValueAsString(books);
		responseWriter.print(serialized);
	}

	private void doSearchBookByISBN(final HttpServletRequest request,
									final HttpServletResponse response,
									final PrintWriter responseWriter,
									final String isbn) throws JsonProcessingException {
		final Optional<Book> book = bookService.searchBookByISBN(isbn);
		if (book.isPresent()) {
			final String serialized = mapper.writeValueAsString(book.get());
			responseWriter.print(serialized);
		} else {
			// Book not found
			sendError("Book not found", 404, response, responseWriter);
		}
	}

	private void doPutBook(final HttpServletRequest request,
						   final HttpServletResponse response,
						   final PrintWriter responseWriter,
						   final Book book) {

		bookService.saveBook(book);
		response.setStatus(204);
	}

	private void doDeleteBookByISBN(final HttpServletRequest request,
									final HttpServletResponse response,
									final PrintWriter responseWriter,
									final String isbn) throws JsonProcessingException {
		bookService.deleteBookByISBN(isbn);
		response.setStatus(204);
	}

	private void sendError(final ErrorDTO error, final int code, final HttpServletResponse response, final PrintWriter responseWriter) {
		try {
			if (responseWriter != null) {
				final String serialized = mapper.writeValueAsString(error);
				responseWriter.print(serialized);
				response.setStatus(code);
			}
		} catch (JsonProcessingException e) {
			// Eat the exception
		}
	}

	private void sendError(final String message, final int code, final HttpServletResponse response, final PrintWriter responseWriter) {
		sendError(new ErrorDTO(message), code, response, responseWriter);
	}

	private void sendError(final String message, final Exception ex, final int code, final HttpServletResponse response, final PrintWriter responseWriter) {
		sendError(new ErrorDTO(message, debugMode ? ex.getStackTrace() : null), code, response, responseWriter);
	}
}
