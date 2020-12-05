# Unidoc

Provides annotations for documenting java source codes.

## Description

Unidoc adds an alternative to documenting java source codes as it introduces the use of element-type-specific annotations. 

The annotations include:

    > @ModuleDoc
    > @PackageDoc
    > @ClassDoc
    > @InterfaceDoc
    > @EnumDoc
    > @AnnotationDoc
    > @AnnotationMemberDoc
    > @ConstructorDoc
    > @ParamDoc
    > @FieldDoc
    > @MethodDoc
     
      
The attributes of each annotation are internally converted to match the description and tags of java doc comments when the command-line tool is run.
    
## Installation

## Usage

An example:

```
Unidoc format:

@ClassDoc(description = "Class description describes the class." + 
          "...", 
          author = "David")
public class ExampleClass {}

```

```
javadoc doc comment:

/**
* Class description describes the class.
* ...
*
* @author David
*/
public class ExampleClass {}

```

  

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Also ensure to update appropriate tests.

## Authors and acknowledgement

Author: \
[Dinneya Charles](https://www.linkedin.com/in/dinneya-charles-a55801139/)

Contributors:\
[David Dinneya](https://www.linkedin.com/in/david-dinneya-aa38ba198/)

## License

[Apache License](http://www.apache.org/licenses/)
 