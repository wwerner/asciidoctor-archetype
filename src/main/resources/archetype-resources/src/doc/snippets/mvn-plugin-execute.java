#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
 public void execute() throws MojoExecutionException, MojoFailureException {
        ensureOutputExists();
        final Asciidoctor asciidoctorInstance = getAsciidoctorInstance();
        final OptionsBuilder optionsBuilder = OptionsBuilder.options().toDir(outputDirectory).compact(compact)
                .safe(SafeMode.UNSAFE).eruby(eruby).backend(backend).docType(doctype).headerFooter(headerFooter);
        final AttributesBuilder attributesBuilder = AttributesBuilder.attributes();
        if (sourceDirectory == null) {
            throw new MojoExecutionException("Required parameter 'asciidoctor.sourceDir' not set.");
        }
        optionsBuilder.baseDir(sourceDirectory).toDir(outputDirectory).destinationDir(outputDirectory).mkDirs(true);
        if (baseDir != null) {
            optionsBuilder.baseDir(baseDir);
        }
        setOptions(optionsBuilder);
        setAttributesOnBuilder(attributesBuilder);
        optionsBuilder.attributes(attributesBuilder.get());
        if (sourceDocumentName == null) {
            for (final File f : scanSourceFiles()) {
                renderFile(asciidoctorInstance, optionsBuilder.asMap(), f);
            }
        } else {
            renderFile(asciidoctorInstance, optionsBuilder.asMap(), new File(sourceDirectory, sourceDocumentName));
        }
    }