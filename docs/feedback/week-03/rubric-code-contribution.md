#### Focused Commits
_Good_

The amount of commits is fine for now, but it should really increase the following weeks. Most commits affect a small number of files and are coherent, which is nice to see,. However the commits messages can really be improved. For example, "temp commit", "added tests" (where and what tests?), "fixed the mistype" (what mistype and where?), and "added a controller class, build fails otherwise" (what class???) should be more detailed and clear. Also 2 commits back to back with the same name should be avoided.


#### Isolation
_Very Good_

There are a decent amount of MR's succesfully merged to main, which is really nice to see already, but should still increase a bit the following weeks. Most MRs are focussed on a single feature, which is good to see. Some MR's are a little small, and could be made bigger in order to push a more complete featureto into main, but this is not a huge issue for now. Some branch names are also not sufficient, like your personal names or "tmp_branch".


#### Reviewability
_Good_

MR's have a clear focus that is reflected in the title and description. Some descriptions are missing + sometimes a little vague, so this can be improved upon (MR 19 for example). Most MR's have a small amount of commits, which is good to see. Some MR's (like MR 14) have too many commits for the size of the changes. 


#### Code Reviews
_Sufficient_

MR's are approved on a timely basis which is good to see. However not all of them are reviewed, which should really be the case. Even a comment that you read all of the code, and mention that this or that looks good, is fine too! Also the MR creator should try to respond to comments that are made in the code review when needed or when a question is asked. The MR can then be iteratively improved by the comments, which I would like to see the coming weeks.



#### Build Server
_Insufficient_

There are frequent commits/pushes which use the build server, however the amount of commits and pushes should increase the following weeks. The build fails sometimes, which is okay, but are not always directly fixed, which is not ideal and should be improved! There is even one open branch with a failing build (which is bad!). Correct me if I'm wrong, but I think you only have the default checkstyle rules, and it would be nice to see at least 10 checkstyle rules implemented for the build server. 


