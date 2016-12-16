#######################################################################
# NOTICE: This script is provided as a sample only, with no warranty
# expressed or implied, and is not supported by XebiaLabs.
#######################################################################
import sys

application = sys.argv[1]
version = sys.argv[2]
environment = sys.argv[3]

searchResults = repository.searchByName(application)
appFullPath = ''
for item in searchResults: 
  if repository.read(item).type == 'udm.Application':
    appFullPath = item
    break

if deployment.isDeployed(appFullPath, environment):
  deploymentRef = deployment.prepareUpgrade('%s/%s' % (appFullPath, version), '%s/%s' % (environment, application))
else:
  deploymentRef = deployment.prepareInitial('%s/%s' % (appFullPath, version), environment)
deploymentRef = deployment.prepareAutoDeployeds(deploymentRef)
taskId = deployment.createDeployTask(deploymentRef).id
deployit.startTaskAndWait(taskId)
print 'Done'
