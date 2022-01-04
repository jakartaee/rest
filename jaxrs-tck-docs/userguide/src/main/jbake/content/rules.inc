///////////////////////////////////////////////////////////////////////
NOTE TO WRITERS:
Most technologies will only need the compatibility rules in rules.adoc.
Some technologies will need additional rules.  If they're needed,
remove the comment block delimiters below and update the rules as
appropriate.  You may need to adjust the rule numbers to avoid gaps.

The first comment block below is additional rules needed by JPA.

The second comment block below is additional rules needed by
JSP and Servlet.  (And EJB, if it had a standalone TCK.)

The third comment block below is additional rules that apply
to any technology that defines deployment descriptors.

The fourth comment block is special rules that apply only to JSP.

NOTE: This set of examples is NOT complete, but should be.
///////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////
*{techID}10* The Runtime must report an error when processing a
Configuration Descriptor that does not conform to the Specifications.

*{techID}11* An error must be reported when processing a configuration
descriptor that includes a Java Persistence QL expression that does not
conform to the Specifications.

*{techID}12* The presence of an XML comment in a Configuration
Descriptor, when processed by the Runtime, must not cause the
functional programmatic behavior of the Runtime to vary from the
functional programmatic behavior of the Runtime in the absence of that
comment.
///////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////
*{techID}10* Each Container must make technically accessible all Java SE
Runtime interfaces and functionality, as defined by the Specifications,
to programs running in the Container, except only as specifically
exempted by these Rules.

*{techID}10.1* Containers may impose security constraints, as defined by
the Specifications.
///////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////
*{techID}11* A Deployment Tool must report an error when processing a
Configuration Descriptor that does not conform to the Specifications.

*{techID}12* The presence of an XML comment in a Configuration
Descriptor, when processed by a Deployment Tool, must not cause the
functional programmatic behavior of the Deployment Tool to vary from
the functional programmatic behavior of the Deployment Tool in the
absence of that comment.
///////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////
*{techID}11* A web Container must report an error, as defined by the
Specifications, when processing a JSP Page that does not conform to the
Specifications.

*{techID}12* The presence of a Java language comment or Java language
directive in a JSP Page that specifies ”java” as the scripting
language, when processed by a web Container, must not cause the
functional programmatic behavior of that JSP Page to vary from the
functional programmatic behavior of that JSP Page in the absence of
that Java language comment or Java language directive.

*{techID}13* The contents of any fixed template data (defined by the
Specifications) in a JSP Page, when processed by a web Container, must
not affect the functional programmatic behavior of that JSP Page,
except as defined by the Specifications.

*{techID}14* The functional programmatic behavior of a JSP Page that
specifies ”java” as the scripting language must be equivalent to the
functional programmatic behavior of the JSP Page Implementation Class
constructed from that JSP Page.
///////////////////////////////////////////////////////////////////////
